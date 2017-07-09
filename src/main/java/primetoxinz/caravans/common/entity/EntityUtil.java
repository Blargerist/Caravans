package primetoxinz.caravans.common.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import sun.misc.UUDecoder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityUtil {
    public static Field isLeashed = ReflectionHelper.findField(EntityLiving.class, "isLeashed", "field_110169_bv");

    public static boolean isLeashed(EntityLiving living) {
        try {
            return (boolean) isLeashed.get(living);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static void setLeashed(EntityLiving living, boolean value) {
        try {
            isLeashed.set(living, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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


}
