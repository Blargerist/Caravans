package primetoxinz.caravans.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import primetoxinz.caravans.ConfigHandler;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.compat.MTGameStages;

import static primetoxinz.caravans.common.entity.EntityUtil.generatePosition;

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
        return "/caravan <player> <caravan name> [max radius] [min radius]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2)
            throw new CommandException(getUsage(sender));
        World world = sender.getEntityWorld();
        EntityPlayer player = getPlayer(server, sender, args[0]);
        CaravanBuilder builder = CaravanAPI.getCaravan(args[1]);
        if (builder == null)
            throw new CommandException("Caravan " + args[1] + " was not found");
        int maxRadius = ConfigHandler.maxRadius;
        int minRadius = ConfigHandler.minRadius;
        if (args.length > 2)
            maxRadius = parseInt(args[2]);
        if (args.length > 3)
            minRadius = parseInt(args[3]);
        Caravan caravan = builder.create(world);
        if (MTGameStages.canSpawnCaravan(player, caravan)) {
            BlockPos pos = generatePosition(world, player.getPosition(), maxRadius, minRadius);
            caravan.spawn(pos, player);
            player.sendStatusMessage(new TextComponentString("A Caravan is arriving"), true);
        } else {
            throw new CommandException("Player cannot spawn this caravan yet");
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
