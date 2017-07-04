package primetoxinz.testmod;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import primetoxinz.testmod.network.MessageSync;
import primetoxinz.testmod.network.NetworkHandler;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class EntityTest extends EntityZombie implements ITest {

    public EntityTest(World worldIn) {
        super(worldIn);
        setHealth(0.001f);
        if (!worldIn.isRemote) {
            working = 1000;
            NetworkHandler.INSTANCE.sendToAll(new MessageSync(this));
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("working"))
            working = compound.getInteger("working");
        System.out.println("read:" + working);
        super.readFromNBT(compound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("working", working);
        System.out.println("write:" + working);
        super.writeEntityToNBT(compound);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityTest.CAPABILITY_TEST)
            return CapabilityTest.CAPABILITY_TEST.cast(this);
        return super.getCapability(capability, facing);
    }

    private int working;

    @Override
    public int isWorking() {
        return working;
    }

    @Override
    public void setWorking(int working) {
        this.working = working;
    }

    @Override
    public int getId() {
        return getEntityId();
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (isServerWorld())
            NetworkHandler.INSTANCE.sendToAll(new MessageSync(this));
        System.out.println((world.isRemote ? "client" : "server") + ":" + working);

        return super.processInteract(player, hand);
    }
}

