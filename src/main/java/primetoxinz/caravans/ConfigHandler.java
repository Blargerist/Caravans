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

    @Config.Comment("Percent chance to spawn a Caravan each day ")
    public static double spawnPercent = 10;

    @Config.Comment("Maximum radius from player to spawn caravan")
    public static int maxRadius = 100;

    @Config.Comment("Minimum radius from player to spawn caravan")
    public static int minRadius = 80;

    @Config.Comment("How long a Caravan stays around in ticks")
    public static int hangoutTicks = 20 * 60;
    @Config.RangeInt(min = 0, max = 24000)
    @Config.Comment("World time to try spawning a Caravan in ticks. A bed wakes up at 0")
    public static int worldTime = 4000;
    @Config.Comment("Surprise!")
    public static boolean surprise = true;

    @Config.Comment("Player Names for skins used by human merchant model")
    public static String[] playerNames = new String[]{"Darkosto"};

    @Config.Comment("Debug Mode")
    public static boolean debug = true;

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(CaravansMod.MODID)) {
            ConfigManager.sync(CaravansMod.MODID, Config.Type.INSTANCE);
        }
    }

}
