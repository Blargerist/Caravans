package primetoxinz.caravans.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.ICaravan;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class CapabilityCaravaner {

    @CapabilityInject(ICaravaner.class)
    public static final Capability<ICaravaner> CARAVANER_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(
                ICaravaner.class,
                new Capability.IStorage<ICaravaner>() {

                    @Nullable
                    @Override
                    public NBTBase writeNBT(Capability<ICaravaner> capability, ICaravaner instance, EnumFacing side) {
                        NBTTagCompound tag = new NBTTagCompound();
                        tag.setString("caravan", instance.getCaravan().getRegistryName().toString());
                        return tag;
                    }

                    @Override
                    public void readNBT(Capability<ICaravaner> capability, ICaravaner instance, EnumFacing side, NBTBase nbt) {
                        NBTTagCompound tag = (NBTTagCompound) nbt;
                        ICaravan caravan = CaravanAPI.CARAVANS.getValue(new ResourceLocation(tag.getString("caravan")));
                        instance.setCaravan(caravan);
                    }
                },
                Caravaner::new
        );
    }
}
