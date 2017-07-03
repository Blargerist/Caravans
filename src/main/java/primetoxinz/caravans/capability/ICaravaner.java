package primetoxinz.caravans.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import primetoxinz.caravans.api.CaravanAPI;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public interface ICaravaner {

    @CapabilityInject(ICaravaner.class)
    Capability<ICaravaner> CARAVANER_CAPABILITY = null;

    ICaravaner setCaravan(ICaravan caravan);

    ICaravan getCaravan();

    BlockPos getPosition();

    ICaravaner spawn(World world, BlockPos pos, ICaravan caravan);

    ICaravaner setTarget(EntityPlayer player);

    int getID();

    class Storage implements Capability.IStorage<ICaravaner> {

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
    }

    class Impl implements ICaravaner {

        public Impl() {
        }

        @Override
        public ICaravaner setCaravan(ICaravan caravan) {
            return null;
        }

        @Override
        public ICaravan getCaravan() {
            return null;
        }

        @Override
        public BlockPos getPosition() {
            return null;
        }

        @Override
        public ICaravaner spawn(World world, BlockPos pos, ICaravan caravan) {
            return this;
        }

        @Override
        public ICaravaner setTarget(EntityPlayer player) {
            return this;
        }

        @Override
        public int getID() {
            return 0;
        }

    }


}
