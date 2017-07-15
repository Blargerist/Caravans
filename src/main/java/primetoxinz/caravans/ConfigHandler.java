package primetoxinz.caravans;

import com.google.common.collect.Lists;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

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

    @Config.Comment("Surprise!")
    public static boolean surprise = true;

    @Config.Comment("Player Names for skins used by human merchant model")
    public static String[] playerNames = new String[]{"Darkosto"};

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(CaravansMod.MODID)) {
            ConfigManager.sync(CaravansMod.MODID, Config.Type.INSTANCE);
        }
    }

}
