package primetoxinz.caravans.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.EntityUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static primetoxinz.caravans.common.CommandCaravan.generatePositions;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class Caravan implements INBTSerializable<NBTTagCompound> {
    protected ResourceLocation name;
    protected World world;

    protected BiMap<Merchant, UUID> merchantMap = HashBiMap.create();
    protected UUID leaderUUID;

    protected List<EntityCaravaneer> entities = Lists.newArrayList();
    protected EntityCaravaneer leader;

    public Caravan(World world, NBTTagCompound tag) {
        this.world = world;
        deserializeNBT(tag);
    }

    public Caravan(ResourceLocation name, World world, Class<? extends EntityCaravaneer> leaderClass, Map<Merchant, Class<? extends EntityCaravaneer>> followerClasses) {
        this.name = name;
        this.world = world;
        this.leader = newInstance(leaderClass, world, this);

        for (Merchant merchant : followerClasses.keySet()) {
            EntityCaravaneer caravaneer = newInstance(followerClasses.get(merchant), world, this);
            merchantMap.put(merchant, caravaneer.getUniqueID());
            entities.add(caravaneer);
        }
    }

    public Optional<EntityCaravaneer> getLeader() {
        if (leader == null && leaderUUID != null) {
            this.leader = getEntity(leaderUUID);
        }
        return Optional.ofNullable(leader);
    }

    public void setLeader(EntityCaravaneer caravaneer) {
        this.leader = caravaneer;
    }


    public boolean isLeader(EntityCaravaneer caravaneer) {
        if (getLeader().isPresent())
            return caravaneer.getUniqueID().equals(getLeader().get().getUniqueID());
        return false;
    }

    public void spawn(BlockPos pos, EntityPlayer player) {
        getLeader().ifPresent(e -> e.setTarget(player));
        getLeader().ifPresent(e -> e.spawn(world, pos));
        List<EntityCaravaneer> entities = getEntities();
        int amount = entities.size();
        List<BlockPos> positions = generatePositions(world, pos, 5, amount);
        for (int i = 0; i < amount; i++) {
            entities.get(i).spawn(world, positions.get(i)).setTarget(player);
        }
    }

    public void open(EntityPlayer player, Entity entity) {
        player.openGui(CaravansMod.MODID, entity.getEntityId(), player.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ);
    }

    public ResourceLocation getName() {
        return name;
    }

    public void sync() {
        for (EntityCaravaneer caravaneer : getEntities()) {
            if (caravaneer != null)
                caravaneer.sync();
        }
        getLeader().ifPresent(e -> e.sync());
    }

    public List<EntityCaravaneer> getEntities() {
        if (entities.isEmpty()) {
            entities = Lists.newArrayList();
            for (UUID uuid : merchantMap.values()) {
                Entity entity = getEntity(uuid);
                if (entity instanceof EntityCaravaneer)
                    entities.add((EntityCaravaneer) entity);
            }
        }
        return entities;
    }

    public Caravan removeFollower(EntityCaravaneer caravaneer) {
        merchantMap.inverse().remove(caravaneer.getUniqueID());
        sync();
        return this;
    }

    public EntityCaravaneer newInstance(Class<? extends EntityCaravaneer> clazz, World world, Caravan caravan) {
        try {
            Constructor<? extends EntityCaravaneer> c = clazz.getConstructor(World.class, Caravan.class);
            EntityCaravaneer t = c.newInstance(world, caravan);
            return t;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return getName().toString();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound caravan = new NBTTagCompound();
        caravan.setString("name", name.toString());
        getLeader().ifPresent( e -> caravan.setString("leader", e.getUniqueID().toString()));
        if (!merchantMap.isEmpty()) {
            NBTTagList list = new NBTTagList();
            NBTTagCompound compound = new NBTTagCompound();
            for (Merchant merchant : merchantMap.keySet()) {
                list.appendTag(new NBTTagString(merchant.toString()));
                compound.setString(merchant.toString(), merchantMap.get(merchant).toString());
            }
            caravan.setTag("merchants", list);
            caravan.setTag("entities", compound);
        }
        return caravan;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey("leader"))
            leaderUUID = UUID.fromString(nbt.getString("leader"));

        name = new ResourceLocation(nbt.getString("name"));

        NBTTagList list = nbt.getTagList("merchants", 8);
        NBTTagCompound compound = nbt.getCompoundTag("entities");
        for (Iterator<NBTBase> it = list.iterator(); it.hasNext(); ) {
            NBTTagString tag = (NBTTagString) it.next();
            String merchant = tag.getString();
            String uuid = compound.getString(merchant);
            merchantMap.put(CaravanAPI.getMerchant(merchant), UUID.fromString(uuid));
        }
        System.out.println("Merchants:" + merchantMap);
    }

    public Merchant getMerchant(UUID uuid) {
        return merchantMap.inverse().get(uuid);
    }

    public EntityCaravaneer getEntity(UUID uuid) {
        return (EntityCaravaneer) EntityUtil.fromUUID(world, uuid);
    }


    public List<Merchant> getMerchants() {
        return Lists.newArrayList(merchantMap.keySet());
    }
}