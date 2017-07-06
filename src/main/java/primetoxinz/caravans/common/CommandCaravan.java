package primetoxinz.caravans.common;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;

import java.util.List;
import java.util.Random;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class CommandCaravan extends CommandBase {
    @Override
    public String getName() {
        return "caravan";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/caravan <player> <caravan name> [radius] ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2)
            throw new CommandException(getUsage(sender));
        World world = sender.getEntityWorld();
        EntityPlayer player = getPlayer(server, sender, args[0]);
        CaravanBuilder builder = CaravanAPI.getCaravan(args[1]);

        int radius = 60;
        if (args.length > 2)
            radius = parseInt(args[2]);
        BlockPos pos = generatePosition(world, player.getPosition(), radius);
        builder.create(world).spawn(pos, player);

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

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
