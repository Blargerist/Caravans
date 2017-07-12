package primetoxinz.caravans;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by primetoxinz on 7/9/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
@Config(modid = CaravansMod.MODID, name = "caravans/caravans")
public class ConfigHandler {

    @Config.Comment("Allow Caravans to randomly spawn")
    public static boolean randomlySpawn = true;

    @Config.Comment("Chance to randomly spawn. 1 in X chance.")
    public static int spawnChance = 3000;

    @Config.Comment("Maximum radius from player to spawn caravan")
    public static int maxRadius = 100;

    @Config.Comment("Minimum radius from player to spawn caravan")
    public static int minRadius = 80;

    @Config.Comment("How long a Caravan stays around in ticks")
    public static int hangoutTicks = 20 * 60;

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(CaravansMod.MODID)) {
            ConfigManager.sync(CaravansMod.MODID, Config.Type.INSTANCE);
        }
    }

}
