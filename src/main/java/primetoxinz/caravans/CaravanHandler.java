package primetoxinz.caravans;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import primetoxinz.caravans.api.CaravanAPI;

import java.util.List;
import java.util.Random;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
public class CaravanHandler {


    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {
        World world = event.getWorld();
        if (world.isRemote)
            return;
        EntityPlayer player = event.getEntityPlayer();
        if (event.getItemStack().getItem() == Items.STICK) {
            BlockPos pos = generatePosition(world, player.getPosition(), 60);
            CaravanAPI.donkey.spawn(world, pos, player);
            player.sendStatusMessage(new TextComponentString("A Caravan is arriving!"), true);
        }
    }

    public static List<BlockPos> generatePositions(World world, BlockPos origin, int radius, int amount) {
        assert (radius * radius * 3) > amount;

        List<BlockPos> positions = Lists.newArrayList();

        while (positions.size() < amount) {
            final BlockPos pos = generatePosition(world, origin, radius);
            if (!positions.stream().anyMatch(p -> p.equals(pos)))
                positions.add(pos);
        }
        return positions;
    }


    public static BlockPos generatePosition(World world, BlockPos origin, int radius) {
        Random rand = world.rand;
        double angle = rand.nextDouble() * 360;
        int x = (int) (origin.getX() + (radius(rand, radius, radius / 2) * Math.cos(Math.toRadians(angle))));
        int z = (int) (origin.getZ() + (radius(rand, radius, radius / 2) * Math.sin(Math.toRadians(angle))));

        return new BlockPos(x, world.getHeight(x, z), z);
    }


    private static double radius(Random random, int max, int min) {
        return (double) (random.nextInt((max - min) + 1) + min);
    }

}
