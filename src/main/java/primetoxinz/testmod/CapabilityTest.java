package primetoxinz.testmod;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class CapabilityTest {

    @CapabilityInject(ITest.class)
    public static Capability<ITest> CAPABILITY_TEST = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(
                ITest.class,
                new Capability.IStorage<ITest>() {
                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<ITest> capability, ITest instance, EnumFacing side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<ITest> capability, ITest instance, EnumFacing side, NBTBase nbt) {

                    }
                }, () -> null);


    }
}
