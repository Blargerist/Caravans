package primetoxinz.caravans.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import primetoxinz.caravans.api.ICaravan;
import primetoxinz.caravans.capability.CapabilityCaravaner;
import primetoxinz.caravans.capability.ICaravaner;
import primetoxinz.caravans.entity.ai.AIGoToPlayer;
import primetoxinz.caravans.entity.ai.AILook;
import primetoxinz.caravans.entity.ai.AISpreadOut;
import primetoxinz.caravans.entity.ai.AIStatus;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class EntityCaravaner extends EntityCreature implements ICaravaner {


    private ICaravan caravan;
    private AIStatus ai;

    public EntityCaravaner(World world) {
        super(world);
        setHealth(0.1f);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(256);
    }

    @Override
    protected void initEntityAI() {
        ai = new AIStatus(Lists.newArrayList(new AIGoToPlayer(this)));
        this.tasks.addTask(1, ai);
        this.tasks.addTask(9, new AILook(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 10f, 0.1f));
        this.tasks.addTask(10, new AISpreadOut(this));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityCaravaner.CARAVANER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityCaravaner.CARAVANER_CAPABILITY)
            return CapabilityCaravaner.CARAVANER_CAPABILITY.cast(this);
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
    public ICaravaner setCaravan(ICaravan caravan) {
        this.caravan = caravan;
        return this;
    }

    @Override
    public ICaravan getCaravan() {
        return caravan;
    }

    @Override
    public ICaravaner spawn(World world, BlockPos pos, ICaravan caravan) {
        this.setCaravan(caravan);
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(this);
        return this;
    }

    @Override
    public ICaravaner setTarget(EntityPlayer player) {
        setAttackTarget(player);
        return this;
    }

    @Override
    public int getID() {
        return getEntityId();
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        System.out.println(player.world.isRemote);
        System.out.println(caravan);
        if (caravan != null) {

            caravan.open(player, hand, this);
            return true;
        }
        return super.processInteract(player, hand);
    }
}
