package primetoxinz.caravans.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.StringUtils;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.EntityUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static primetoxinz.caravans.common.entity.EntityUtil.generatePositions;

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
    protected Status status = Status.ARRIVING;

    protected String stage;

    public Caravan(World world, NBTTagCompound tag) {
        this.world = world;
        deserializeNBT(tag);
    }

    public Caravan(ResourceLocation name, World world, Class<? extends EntityCaravaneer> leaderClass, Map<MerchantBuilder, Class<? extends EntityCaravaneer>> followerClasses) {
        this.name = name;
        this.world = world;
        this.leader = newInstance(leaderClass, world, this);

        for (MerchantBuilder merchant : followerClasses.keySet()) {
            EntityCaravaneer caravaneer = newInstance(followerClasses.get(merchant), world, this);
            merchantMap.put(merchant.create(), caravaneer.getUniqueID());
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

    public BlockPos getPosition() {
        return getLeader().isPresent() ? getLeader().get().getPosition() : null;
    }

    public boolean isLeader(EntityCaravaneer caravaneer) {
        if (getLeader().isPresent())
            return caravaneer.getUniqueID().equals(getLeader().get().getUniqueID());
        return false;
    }

    public void spawn(BlockPos pos, EntityPlayer player) {
        getLeader().ifPresent(e -> e.setTarget(player));
        getLeader().ifPresent(e -> e.spawn(world, pos, Status.ARRIVING));
        List<EntityCaravaneer> entities = getEntities();
        int amount = entities.size();
        List<BlockPos> positions = generatePositions(world, pos, 5, amount);

        for (int i = 0; i < amount; i++) {
            entities.get(i).spawn(world, positions.get(i), Status.ARRIVING).setTarget(player);
        }
    }

    public void openFirst(EntityPlayer player) {
        if (!getEntities().isEmpty()) {
            open(player, getEntities().get(0));
        }
    }

    public void open(EntityPlayer player, EntityCaravaneer caravaneer) {
        player.openGui(CaravansMod.MODID, caravaneer.getEntityId(), player.world, (int) caravaneer.posX, (int) caravaneer.posY, (int) caravaneer.posZ);
        world.playSound(player, player.getPosition(), caravaneer.getTradeSound(), SoundCategory.NEUTRAL, 0.5f, 1.0f);

    }

    public ResourceLocation getName() {
        return name;
    }

    public String getRealName() {
        return StringUtils.capitalize(getName().getResourcePath());
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
            entities.sort(Comparator.comparing(Entity::getUniqueID));
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

        getLeader().ifPresent(e -> caravan.setString("leader", e.getUniqueID().toString()));
        caravan.setString("name", name.toString());
        caravan.setInteger("status", status.ordinal());
        if (!merchantMap.isEmpty()) {
            NBTTagList merchants = new NBTTagList();
            for (Merchant m : merchantMap.keySet()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setString("entity", merchantMap.get(m).toString());
                tag.setTag("merchant", m.serializeNBT());
                merchants.appendTag(tag);
            }
            caravan.setTag("merchants", merchants);
        }
        return caravan;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("leader")) {
            leaderUUID = UUID.fromString(nbt.getString("leader"));
        }
        name = new ResourceLocation(nbt.getString("name"));
        status = Status.VALUES[nbt.getInteger("status")];

        NBTTagList merchants = nbt.getTagList("merchants", 10);
        for (Iterator<NBTBase> i = merchants.iterator(); i.hasNext(); ) {
            NBTTagCompound tag = (NBTTagCompound) i.next();
            UUID uuid = UUID.fromString(tag.getString("entity"));
            merchantMap.put(new Merchant((NBTTagCompound) tag.getTag("merchant")), uuid);
        }

    }

    public Merchant getMerchant(UUID uuid) {
        return merchantMap.inverse().get(uuid);
    }

    public EntityCaravaneer getEntity(UUID uuid) {
        return (EntityCaravaneer) EntityUtil.fromUUID(world, uuid);
    }

    public EntityCaravaneer getEntity(Merchant merchant) {
        return getEntity(merchantMap.get(merchant));
    }

    public List<Merchant> getMerchants() {
        List<Merchant> merchants = Lists.newArrayList(merchantMap.keySet());
        merchants.sort(Comparator.comparing(Merchant::getName));
        return merchants;
    }

    public void nextStatus() {
        this.status = Status.VALUES[(status.ordinal() + 1) % (Status.VALUES.length + 1)];
    }

    public void onFinish(EntityCaravaneer caravaneer) {
        nextStatus();
        caravaneer.getState().setAction(this.status);
        for (EntityCaravaneer follower : getEntities()) {
            follower.getState().setAction(this.status);
        }
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        ARRIVING,
        TRADING,
        LEAVING,
        GONE;
        public static Status[] VALUES = values();
    }

    public void setGameStage(String stage) {
        this.stage = stage;
    }

    public String getGameStage() {
        return stage;
    }
}