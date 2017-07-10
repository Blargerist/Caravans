package primetoxinz.caravans.common.entity;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import primetoxinz.caravans.api.*;
import primetoxinz.caravans.common.entity.ai.AISpreadOut;
import primetoxinz.caravans.common.entity.ai.AIState;
import primetoxinz.caravans.common.entity.ai.AIWanderNear;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.network.NetworkMessage;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/3/17.
 */
public abstract class EntityCaravaneer extends EntityCreature implements ICaravaneer, IEntityAdditionalSpawnData {

    private AIState state;
    private Caravan caravan;
    private boolean leader;
    private BlockPos origins;
    public int stay;
    private UUID target;
    private List<EntityLiving> tradeEntities = Lists.newArrayList();
    private List<UUID> tradeUUIDs = Lists.newArrayList();

    public EntityCaravaneer(World worldIn) {
        super(worldIn);
    }

    @SuppressWarnings("unused")
    public EntityCaravaneer(World worldIn, Caravan caravan) {
        this(worldIn);
        this.caravan = caravan;
        this.setHealth(0.0001f);
    }

    @Override
    public void onEntityUpdate() {
        if (getState() != null && !getState().hasAction())
            getState().setAction(getCaravan().getStatus());
        if (getCaravan().getStatus() == Caravan.Status.ARRIVING && target != null) {
            setTarget(EntityUtil.playerFromUUID(world, target));
        }
        super.onEntityUpdate();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, state = new AIState(this));
        this.tasks.addTask(2, new AISpreadOut(this));
        this.tasks.addTask(3, new AIWanderNear(this, 100));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256);
    }

    @Override
    public ICaravaneer setCaravan(Caravan caravan) {
        this.caravan = caravan;
        return this;
    }

    @Override
    public Caravan getCaravan() {
        return caravan;
    }

    public Merchant getMerchant() {
        return getCaravan().getMerchant(this.getUniqueID());
    }

    @Override
    public ICaravaneer spawn(World world, BlockPos pos, Caravan.Status status) {
        if (isServerWorld()) {
            this.getState().setAction(status);
            this.setPosition(pos.getX(), pos.getY(), pos.getZ());
            this.origins = pos;
            this.forceSpawn = true;
            world.spawnEntity(this);
            sync();
            if (!isLeader()) {
                for (ITrade trade : getMerchant().getTrades()) {
                    if (trade instanceof IEntityTrade) {
                        Class<? extends EntityLiving> entity = ((IEntityTrade) trade).getOutput();
                        EntityLiving living = EntityUtil.createEntity(entity, world);
                        living.setPosition(pos.getX(), pos.getY(), pos.getZ());
                        living.setLeashedToEntity(this, true);
                        living.setEntityInvulnerable(true);
                        tradeEntities.add(living);
                        world.spawnEntity(living);
                    }
                }
            }
        }
        return this;
    }

    @Override
    public ICaravaneer setTarget(EntityPlayer player) {
        setAttackTarget(player);
        return this;
    }

    @Override
    public int getID() {
        return getEntityId();
    }


    public boolean isLeader() {
        if (getCaravan() != null)
            return getCaravan().isLeader(this);
        return false;
    }

    @Override
    public void setDead() {
        super.setDead();
        sync();
        if (caravan != null) {
            if (isLeader()) {
                this.caravan.setLeader(null);
            } else {
                this.caravan.removeFollower(this);
            }
            List<EntityLiving> trades = getTradeEntities();
            if (!trades.isEmpty()) {
                for (EntityLiving living : trades) {
                    if (living != null)
                        living.setDead();
                }
            }
        }

    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setTag("caravan", caravan.serializeNBT());
        if (getAttackTarget() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) getAttackTarget();
            compound.setString("player", player.getGameProfile().getId().toString());
        }
        compound.setBoolean("leader", leader);
        if (origins != null)
            compound.setLong("origins", origins.toLong());
        compound.setInteger("stay", stay);

        if (!tradeEntities.isEmpty()) {
            NBTTagList tradeEntities = new NBTTagList();
            for (EntityLiving e : this.tradeEntities) {
                tradeEntities.appendTag(new NBTTagString(e.getUniqueID().toString()));
            }
            compound.setTag("tradeEntities", tradeEntities);
        }
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("caravan")) {
            this.caravan = new Caravan(world, compound.getCompoundTag("caravan"));
        }
        if (compound.hasKey("origins"))
            this.origins = BlockPos.fromLong(compound.getLong("origins"));
        if (compound.hasKey("player")) {
            this.target = UUID.fromString(compound.getString("player"));
        }
        this.leader = compound.getBoolean("leader");
        this.stay = compound.getInteger("stay");

        if (compound.hasKey("tradeEntities")) {
            NBTTagList trades = compound.getTagList("tradeEntities", 8);
            for (Iterator<NBTBase> it = trades.iterator(); it.hasNext(); ) {
                NBTTagString uuid = (NBTTagString) it.next();
                tradeUUIDs.add(UUID.fromString(uuid.getString()));
            }
        }
        super.readFromNBT(compound);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        sync();
        if (isLeader() && getCaravan().getStatus() == Caravan.Status.TRADING) {
            getCaravan().openFirst(player);
            return true;
        }
        return false;
    }

    public void sync() {
        if (isServerWorld()) {
            NetworkHandler.INSTANCE.sendToAll(new MessageCaravan(this));
        }
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        if (getCaravan() != null) {
            NetworkMessage.writeNBT(getCaravan().serializeNBT(), buffer);
        }
        if (origins != null)
            NetworkMessage.writeBlockPos(origins, buffer);
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        if (getCaravan() == null) {
            setCaravan(new Caravan(world, NetworkMessage.readNBT(buffer)));
        }
        if (origins == null)
            this.origins = NetworkMessage.readBlockPos(buffer);
        System.out.println(origins);
    }

    public BlockPos getOrigins() {
        return origins;
    }

    public AIState getState() {
        return state;
    }

    public List<EntityLiving> getTradeEntities() {
        if (tradeEntities.isEmpty()) {
            tradeEntities = tradeUUIDs.stream().map(u -> (EntityLiving) EntityUtil.fromUUID(world, u)).collect(Collectors.toList());
        }
        return tradeEntities;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Nullable
    @Override
    protected abstract SoundEvent getHurtSound();

    @Nullable
    @Override
    protected abstract SoundEvent getDeathSound();

    @Nullable
    @Override
    protected abstract SoundEvent getAmbientSound();

    public abstract SoundEvent getTradeSound();

}

