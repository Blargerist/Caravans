package primetoxinz.caravans.common;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.compat.MTGameStages;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return "/caravan <player> <caravan name>|random [max radius] [min radius]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2)
            throw new CommandException(getUsage(sender));
        World world = sender.getEntityWorld();
        EntityPlayer player = getPlayer(server, sender, args[0]);
        CaravanBuilder builder;
        if (args[1].equalsIgnoreCase("random"))
            builder = CaravanAPI.getRandomCaravan(world);
        else
            builder = CaravanAPI.getCaravan(args[1]);
        if (builder == null)
            throw new CommandException("Caravan " + args[1] + " was not found");
        int maxRadius = CaravansMod.ConfigHandler.maxRadius;
        int minRadius = CaravansMod.ConfigHandler.minRadius;
        if (args.length > 2)
            maxRadius = parseInt(args[2]);
        if (args.length > 3)
            minRadius = parseInt(args[3]);
        Caravan caravan = builder.create(world);
        if (MTGameStages.canSpawnCaravan(player, caravan)) {
            BlockPos pos = generatePosition(world, player.getPosition(), maxRadius, minRadius);
            EntityUtil.spawnCaravan(world, player, pos, caravan);
        } else {
            throw new CommandException("Player cannot spawn this caravan yet");
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        switch (args.length) {
            case 1:
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            case 2:
                return getListOfStringsMatchingLastWord(args, getCaravans());
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }


    public List<String> getCaravans() {
        return StreamSupport.stream(CaravanAPI.CARAVANS.spliterator(), false).map(c -> c.getRegistryName().getResourcePath()).map(s -> s.replaceAll(" ", "_")).collect(Collectors.toList());
    }
}
