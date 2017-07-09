package primetoxinz.caravans.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.ICaravaneer;
import primetoxinz.caravans.common.entity.ai.AISpreadOut;
import primetoxinz.caravans.common.entity.ai.AIState;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.network.NetworkMessage;

import java.util.UUID;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class EntityCaravaneer extends EntityCreature implements ICaravaneer, IEntityAdditionalSpawnData {

    private AIState state;
    private Caravan caravan;
    private boolean leader;
    private BlockPos origins;
    public int stay;
    private UUID target;

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
            setTarget( EntityUtil.playerFromUUID(world,target));
        }
        super.onEntityUpdate();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, state = new AIState(this));
        this.tasks.addTask(2, new AISpreadOut(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
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

    @Override
    public ICaravaneer spawn(World world, BlockPos pos, Caravan.Status status) {
        if (isServerWorld()) {
            this.getState().setAction(status);
            this.setPosition(pos.getX(), pos.getY(), pos.getZ());
            this.origins = pos;
            this.forceSpawn = true;
            world.spawnEntity(this);
            sync();
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
    public void onDeath(DamageSource cause) {
        sync();
        if (caravan != null) {
            if (isLeader()) {
                this.caravan.setLeader(null);
            } else {
                this.caravan.removeFollower(this);
            }
        }
        super.onDeath(cause);
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
        super.readFromNBT(compound);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        sync();
        if (isLeader()) {
            getCaravan().open(player, this);
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
}
