package primetoxinz.caravans.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import primetoxinz.caravans.CaravanHandler;
import primetoxinz.caravans.Caravans;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/1/17.
 */
@SuppressWarnings("unused")
public interface ICaravan extends IForgeRegistryEntry<ICaravan> {

    BlockPos getPosition(World world);

    CaravanStatus getStatus();

    ICaravan setStatus(CaravanStatus status);

    List<ICaravaner> getFollowers(World world);

    ICaravan addFollower(Class caravaner);

    ICaravan generateFollowers(int amount);

    ICaravan setLeader(Class caravaner);

    ICaravaner getLeader(World world);

    void spawn(World world, BlockPos pos, EntityPlayer player);

    void open(EntityPlayer player, EnumHand hand, Entity entity);

    enum CaravanStatus {
        ARRIVING,
        SELLING,
        LEAVING,
        LEAVE;
        public static CaravanStatus[] VALUES = values();
    }

    class Storage implements Capability.IStorage<ICaravan> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ICaravan> capability, ICaravan instance, EnumFacing side) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("status", instance.getStatus().ordinal());
            return compound;
        }

        @Override
        public void readNBT(Capability<ICaravan> capability, ICaravan instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            instance.setStatus(CaravanStatus.VALUES[tag.getInteger("status")]);
        }
    }


    class Caravan<C extends Entity & ICaravaner> implements ICaravan {

        protected CaravanStatus status;
        protected Class<C> leader;
        protected List<Class<C>> followers;
        protected ResourceLocation name;

        public Caravan(String name, Class<C> leader, List<Class<C>> followers) {
            setRegistryName(new ResourceLocation(Caravans.MODID, name));
            this.status = CaravanStatus.ARRIVING;
            this.leader = leader;
            this.followers = followers;
        }

        @Override
        public void spawn(World world, BlockPos pos, EntityPlayer player) {
            ICaravaner leader = getLeader(world).setTarget(player);
            leader.spawn(world, pos, this);
            List<ICaravaner> followers = getFollowers(world);
            int amount = followers.size();
            List<BlockPos> positions = CaravanHandler.generatePositions(world, pos, 5, amount);
            for (int i = 0; i < amount; i++) {
                followers.get(i).spawn(world, positions.get(i), this).setTarget(player);
            }
        }

        @Override
        public void open(EntityPlayer player, EnumHand hand, Entity entity) {
            player.openGui(Caravans.MODID, entity.getEntityId(), player.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ);
        }

        @Override
        public BlockPos getPosition(World world) {
            return getLeader(world).getPosition();
        }


        @Override
        public CaravanStatus getStatus() {
            return status;
        }

        @Override
        public ICaravan setStatus(CaravanStatus status) {
            this.status = status;
            return this;
        }

        @Override
        public List<ICaravaner> getFollowers(World world) {
            return followers.stream().map(c -> newInstance(c, world)).collect(Collectors.toList());
        }

        @Override
        public ICaravan addFollower(Class caravaner) {
            followers.add(caravaner);
            return this;
        }

        @Override
        public ICaravan generateFollowers(int amount) {
            //TODO
            return null;
        }

        @Override
        public ICaravan setLeader(Class caravaner) {
            return null;
        }

        @Override
        public ICaravaner getLeader(World world) {
            return newInstance(leader, world);
        }

        @Override
        public ICaravan setRegistryName(ResourceLocation name) {
            this.name = name;
            return this;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return name;
        }

        @Override
        public Class<ICaravan> getRegistryType() {
            return ICaravan.class;
        }

        public C newInstance(Class<C> clazz, World world) {
            try {
                Constructor<C> c = clazz.getConstructor(World.class);
                C t = c.newInstance(world);
                return t;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
