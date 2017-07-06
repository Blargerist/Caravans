package primetoxinz.caravans.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.IEntityListen;
import primetoxinz.caravans.capability.ICaravaneer;
import primetoxinz.caravans.common.entity.ai.AIGoToPlayer;
import primetoxinz.caravans.common.entity.ai.AIStatus;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class EntityCaravaneer extends EntityCreature implements ICaravaneer, IEntityListen {

    private Caravan caravan;
    private boolean leader;

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
        super.onEntityUpdate();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new AIStatus(new AIGoToPlayer(this)));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
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
    public ICaravaneer spawn(World world, BlockPos pos) {
        if (isServerWorld()) {
            this.setPosition(pos.getX(), pos.getY(), pos.getZ());
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
        compound.setBoolean("leader", leader);
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("caravan")) {
            this.caravan = new Caravan(world, compound.getCompoundTag("caravan"));
        }
        this.leader = compound.getBoolean("leader");
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
    public void onAdded() {
        sync();
        System.out.println("syncing!");
    }
}
