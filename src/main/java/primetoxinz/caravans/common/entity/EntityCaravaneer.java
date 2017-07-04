package primetoxinz.caravans.common.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.ICaravan;
import primetoxinz.caravans.capability.CapabilityCaravaneer;
import primetoxinz.caravans.capability.ICaravaneer;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class EntityCaravaneer extends EntityCreature implements ICaravaneer {

    private ICaravan caravan;

    public EntityCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntityCaravaneer(World worldIn, ICaravan caravan) {
        this(worldIn);
        this.caravan = caravan;
    }

    @Override
    public ICaravaneer setCaravan(ICaravan caravan) {
        this.caravan = caravan;
        return this;
    }

    @Override
    public ICaravan getCaravan() {
        return caravan;
    }

    @Override
    public ICaravaneer spawn(World world, BlockPos pos) {
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(this);
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

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityCaravaneer.CARAVANEER_CAPABILITY)
            return CapabilityCaravaneer.CARAVANEER_CAPABILITY.cast(this);
        return super.getCapability(capability, facing);
    }


    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setString("caravan", caravan.toString());
        System.out.println("write:" + caravan.toString());
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("caravan")) {
            String c = compound.getString("caravan");
            System.out.println("read:" + c);
            setCaravan(CaravanAPI.getCaravan(c));
        }
        super.readFromNBT(compound);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (isServerWorld()) {
            NetworkHandler.INSTANCE.sendToAll(new MessageCaravan(this));
        }
        System.out.println((world.isRemote ? "client" : "server") + ":" + caravan);
        return super.processInteract(player, hand);
    }
}
