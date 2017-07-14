package primetoxinz.caravans.common.entity;

import com.google.common.collect.Lists;
import minetweaker.MineTweakerAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityUtil {
    public static Field isLeashed = ReflectionHelper.findField(EntityLiving.class, "isLeashed", "field_110169_bv");

    public static Field leashedToEntity = ReflectionHelper.findField(EntityLiving.class, "leashedToEntity", "field_110168_bw");

    public static Entity getLeashedTo(EntityLivingBase living) {
        try {
            return (Entity) leashedToEntity.get(living);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isLeashed(EntityLiving living) {
        try {
            return (boolean) isLeashed.get(living);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static void setLeashed(EntityLivingBase attach, EntityLiving living, boolean value) {
        living.setLeashedToEntity(attach, value);
//        NetworkHandler.sendToAllAround(new MessageSyncLeash(living, attach), attach.world, attach.getPosition(), 100);
    }

    public static Field bodyHelper = ReflectionHelper.findField(EntityLiving.class, "bodyHelper", "field_70762_j");

    public static void updateBody(EntityLiving living) {
        try {
            EntityBodyHelper helper = (EntityBodyHelper) bodyHelper.get(living);
            if (helper != null) {
                helper.updateRenderAngles();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static EntityPlayer playerFromUUID(World world, String uuid) {
        return playerFromUUID(world, UUID.fromString(uuid));
    }

    public static EntityPlayer playerFromUUID(World world, UUID uuid) {
        return world.getPlayerEntityByUUID(uuid);
    }

    public static Entity fromUUID(World world, String uuid) {
        return fromUUID(world, UUID.fromString(uuid));
    }

    public static Entity fromUUID(World world, UUID uuid) {
        if (uuid != null) {
            for (Entity entity : world.loadedEntityList) {
                if (entity.getUniqueID().equals(uuid))
                    return entity;
            }
        }
        return null;
    }

    public static EntityPlayer getRandomPlayer(World world) {
        List<EntityPlayer> players = world.playerEntities;
        if (!players.isEmpty()) {
            int i = world.rand.nextInt(players.size());
            return players.get(i);
        }
        return null;
    }

    public static List<BlockPos> generatePositions(World world, BlockPos origin, int radius, int amount) {
        assert (radius * radius * 3) > amount;

        List<BlockPos> positions = Lists.newArrayList();

        while (positions.size() < amount) {
            final BlockPos pos = generatePosition(world, origin, radius, radius / 2);
            if (!positions.stream().anyMatch(p -> p.equals(pos)))
                positions.add(pos);
        }
        return positions;
    }


    public static BlockPos generatePosition(World world, BlockPos origin, int radius, int innerRadius) {
        Random rand = world.rand;
        double angle = rand.nextDouble() * 360;
        int x = (int) (origin.getX() + (radius(rand, radius, innerRadius) * Math.cos(Math.toRadians(angle))));
        int z = (int) (origin.getZ() + (radius(rand, radius, innerRadius) * Math.sin(Math.toRadians(angle))));

        return new BlockPos(x, world.getHeight(x, z), z);
    }

    private static double radius(Random random, int max, int min) {
        return (double) (random.nextInt((max - min) + 1) + min);
    }

    public static EntityLiving createEntity(Class<? extends EntityLiving> clazz, World world) {
        try {
            Constructor constructor = clazz.getConstructor(World.class);
            EntityLiving entity = (EntityLiving) constructor.newInstance(world);
            return entity;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<? extends EntityLiving> getEntity(String entity) {
        Class<? extends EntityLiving> clazz;
        try {
            clazz = (Class<? extends EntityLiving>) Class.forName(entity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            MineTweakerAPI.logError(entity + " cannot be found!");
            return null;
        }
        return clazz;
    }

    public static EntityLiving setPosition(EntityLiving entityLiving, BlockPos pos) {
        entityLiving.setPosition(pos.getX(), pos.getY(), pos.getZ());
        return entityLiving;
    }


    public static EntityLiving getLeashed(Class<? extends EntityLiving> clazz, World world, EntityLivingBase living) {
        for (Entity e : world.loadedEntityList) {
            if (clazz.isAssignableFrom(e.getClass())) {
                EntityLiving leashed = (EntityLiving) e;
                Entity leashedTo = getLeashedTo(leashed);
                if (leashedTo != null && leashedTo.getUniqueID().equals(living.getUniqueID())) {
                    return leashed;
                }
            }
        }
        return null;
    }

    public static boolean takeLeashed(Class<? extends EntityLiving> clazz, EntityLivingBase living) {
        EntityLiving leashed = EntityUtil.getLeashed(clazz, living.world, living);
        if (leashed != null) {
            leashed.setDead();
            return true;
        }
        return false;
    }

    public static boolean giveLeashed(Class<? extends EntityLiving> clazz, EntityLivingBase attach) {
        EntityLiving entity = EntityUtil.setPosition(EntityUtil.createEntity(clazz, attach.world), attach.getPosition());
        entity.setHealth(0.01f);
        if (attach instanceof EntityLivingBase)
            entity.setEntityInvulnerable(true);
        boolean spawned = attach.world.spawnEntity(entity);
        setLeashed(attach, entity, true);
        return spawned;
    }
}
