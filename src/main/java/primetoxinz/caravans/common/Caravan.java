package primetoxinz.caravans.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.ICaravan;
import primetoxinz.caravans.capability.ICaravaneer;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class Caravan<C extends Entity & ICaravaneer> implements ICaravan {

    protected CaravanStatus status;
    protected Class<C> leader;
    protected List<Class<C>> followers;
    protected ResourceLocation name;

    public Caravan(String name, Class<C> leader, List<Class<C>> followers) {
        setRegistryName(new ResourceLocation(CaravansMod.MODID, name));
        this.status = CaravanStatus.ARRIVING;
        this.leader = leader;
        this.followers = followers;
    }

    @Override
    public void spawn(World world, BlockPos pos, EntityPlayer player) {
        ICaravaneer leader = getLeader(world).setTarget(player);
        leader.spawn(world, pos, this);
//        List<ICaravaneer> followers = getFollowers(world);
//        int amount = followers.size();
//        List<BlockPos> positions = CaravanHandler.generatePositions(world, pos, 5, amount);
//        for (int i = 0; i < amount; i++) {
//            followers.get(i).spawn(world, positions.get(i), this).setTarget(player);
//        }
    }

    @Override
    public void open(EntityPlayer player, EnumHand hand, Entity entity) {
        player.openGui(CaravansMod.MODID, entity.getEntityId(), player.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ);
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
    public List<ICaravaneer> getFollowers(World world) {
        return followers.stream().map(c -> newInstance(c, world,this)).collect(Collectors.toList());
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
    public ICaravaneer getLeader(World world) {
        return newInstance(leader, world, this);
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

    public C newInstance(Class<C> clazz, World world, ICaravan caravan) {
        try {
            Constructor<C> c = clazz.getConstructor(World.class, ICaravan.class);
            C t = c.newInstance(world,caravan);
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}