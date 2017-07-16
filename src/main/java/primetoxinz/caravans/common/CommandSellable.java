package primetoxinz.caravans.common;


import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.commands.CTChatCommand;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.util.Iterator;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class CommandSellable extends CraftTweakerCommand {

    public CommandSellable() {
        super("sellableEntities");
    }

    @Override
    protected void init() {
        setDescription(
                SpecialMessagesChat.getClickableCommandText("\u00A72/ct sellableEntities", "/ct sellableEntities", true),
                SpecialMessagesChat.getNormalMessage(" \u00A73Caravans : Outputs a list of all entity classPaths that can be sold in the game to the CraftTweaker log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        Iterator<EntityEntry> iter = ForgeRegistries.ENTITIES.iterator();
        CraftTweakerAPI.logCommand("Sellable Entities:");
        while (iter.hasNext()) {
            EntityEntry e = iter.next();
            Class clazz = e.getEntityClass();
            if (EntityLiving.class.isAssignableFrom(clazz) && !EntityCaravaneer.class.isAssignableFrom(clazz)) {
                CraftTweakerAPI.logCommand(e.getEntityClass().toString());
            }
        }
        if (sender != null)
            sender.sendMessage(new TextComponentString("List generated; see minetweaker.log in your minecraft dir"));
    }
}
