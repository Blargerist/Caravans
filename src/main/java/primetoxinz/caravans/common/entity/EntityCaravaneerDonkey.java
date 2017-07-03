package primetoxinz.caravans.common.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import primetoxinz.caravans.api.ICaravan;
import primetoxinz.caravans.capability.CapabilityCaravaneer;
import primetoxinz.caravans.capability.ICaravaneer;
import primetoxinz.caravans.common.entity.ai.AIGoToPlayer;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityCaravaneerDonkey extends EntityDonkey implements ICaravaneer {

    private ICaravan caravan;

    public EntityCaravaneerDonkey(World worldIn) {
        super(worldIn);
        setChested(worldIn.rand.nextBoolean());
        setHealth(0.1f);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (caravan != null) {
            caravan.open(player, hand, this);
            return true;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource cause) {
        //NO-OP
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new AIGoToPlayer(this));
    }

    @Override
    public ICaravan getCaravan() {
        return caravan;
    }

    @Override
    public ICaravaneer spawn(World world, BlockPos pos, ICaravan caravan) {
        this.caravan = caravan;
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(this);
        return this;
    }


    @Override
    public ICaravaneer setTarget(EntityPlayer player) {
        this.setAttackTarget(player);
        return this;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityCaravaneer.CARAVANER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityCaravaneer.CARAVANER_CAPABILITY)
            return CapabilityCaravaneer.CARAVANER_CAPABILITY.cast(this);
        return super.getCapability(capability, facing);
    }

    @Override
    public void clearLeashed(boolean sendPacket, boolean dropLead) {
        if (EntityUtil.isLeashed(this)) {
            setLeashedToEntity(null, true);
            EntityUtil.setLeashed(this, false);
            if (!this.world.isRemote && sendPacket && this.world instanceof WorldServer) {
                ((WorldServer) this.world).getEntityTracker().sendToTracking(this, new SPacketEntityAttach(this, null));
            }
        }
    }

    @Override
    public int getID() {
        return getEntityId();
    }

    @Override
    public ICaravaneer setCaravan(ICaravan caravan) {
        this.caravan = caravan;
        return this;
    }
}
