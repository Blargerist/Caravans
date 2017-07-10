package primetoxinz.caravans.common;

import minetweaker.MineTweakerAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.util.Iterator;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class CommandSellable implements ICommandFunction {
    @Override
    public void execute(String[] strings, IPlayer player) {
        Iterator<EntityEntry> iter = ForgeRegistries.ENTITIES.iterator();
        MineTweakerAPI.logCommand("Sellable Entitie:");
        while (iter.hasNext()) {
            EntityEntry e = iter.next();
            Class clazz = e.getEntityClass();
            if (EntityLiving.class.isAssignableFrom(clazz) && !EntityCaravaneer.class.isAssignableFrom(clazz)) {
                MineTweakerAPI.logCommand(e.getEntityClass().toString());
            }
        }

        if (player != null) {
            player.sendChat("List generated; see minetweaker.log in your minecraft dir");
        }

    }
}
