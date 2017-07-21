package primetoxinz.caravans;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.MerchantBuilder;
import primetoxinz.caravans.client.gui.GuiHandler;
import primetoxinz.caravans.common.CommandCaravan;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.common.entity.types.*;
import primetoxinz.caravans.compat.MTCompat;
import primetoxinz.caravans.compat.MTGameStages;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.MessageEntityTrade;
import primetoxinz.caravans.network.MessageSyncLeash;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.proxy.CommonProxy;

import java.io.File;
import java.util.Random;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
@Mod(modid = CaravansMod.MODID, name = CaravansMod.NAME, version = CaravansMod.VERSION, dependencies = CaravansMod.DEP)
public class CaravansMod {
    public static final String MODID = "caravans";
    public static final String NAME = "Caravans";
    public static final String VERSION = "{version}";
    public static final String DEP = "required-after:crafttweaker";


    @SidedProxy(modId = MODID, clientSide = "primetoxinz.caravans.proxy.ClientProxy", serverSide = "primetoxinz.caravans.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(owner = MODID)
    public static CaravansMod INSTANCE;


    public File caravansFolder;

    public static SoundEvent SPECIAL;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerEntity(EntityVillagerCaravaneer.class, "caravaner.villager", 256, 1, true);
        registerEntity(EntityZombieCaravaneer.class, "caravaner.zombie", 256, 1, true);
        registerEntity(EntitySkeletonCaravaneer.class, "caravaner.skeleton", 256, 1, true);
        registerEntity(EntitySpiderCaravaneer.class, "caravaner.spider", 256, 1, true);
        registerEntity(EntityCreeperCaravaneer.class, "caravaner.creeper", 256, 1, true);
        registerEntity(EntityHumanCaravaneer.class, "caravaner.human", 256, 1, true);
        NetworkRegistry.INSTANCE.registerGuiHandler(CaravansMod.INSTANCE, new GuiHandler());
        NetworkHandler.register(MessageCaravan.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncLeash.class, Side.CLIENT);
        NetworkHandler.register(MessageEntityTrade.class, Side.SERVER);
        proxy.preInit(event);
        caravansFolder = new File(event.getModConfigurationDirectory(), CaravansMod.MODID);
        if (!caravansFolder.exists())
            caravansFolder.mkdirs();
        SPECIAL = registerSound("caravans.special");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        MTCompat.preInit();

    }


    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandCaravan());
    }


    public static SoundEvent registerSound(String soundName) {
        ResourceLocation soundID = new ResourceLocation(MODID, soundName);
        return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
    }

    private static int availableEntityId;

    /**
     * Registers an entity for this mod. Handles automatic available ID
     * assignment.
     */
    public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
                                      int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, entityName), entityClass, entityName, availableEntityId, INSTANCE, trackingRange,
                updateFrequency, sendsVelocityUpdates);
        availableEntityId++;
    }


    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        new RegistryBuilder<CaravanBuilder>()
                .setType(CaravanBuilder.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .setName(new ResourceLocation(CaravansMod.MODID, "caravans")).create();

        new RegistryBuilder<MerchantBuilder>()
                .setType(MerchantBuilder.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .setName(new ResourceLocation(CaravansMod.MODID, "merchants")).create();
    }


    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!ConfigHandler.randomlySpawn)
            return;
        World world = event.world;
        Random rand = world.rand;
        if (world.getMinecraftServer().getPlayerList().getPlayers().isEmpty()) {
            if (ConfigHandler.debug)
                FMLLog.warning("Caravans:No Players Found");
            return;
        }

        if (event.phase == TickEvent.Phase.END && (world.getWorldTime() % 24000) == ConfigHandler.worldTime) {

            double random = rand.nextDouble();
            double chance = ConfigHandler.spawnPercent / 100d;
            if (ConfigHandler.debug)
                FMLLog.warning("Caravans:Attempting to spawn Caravan. Config Chance: %s Random Chance: %s", chance, random);
            if (random <= chance) {
                CaravanBuilder builder = CaravanAPI.getRandomCaravan(world);
                EntityPlayer player = EntityUtil.getRandomPlayer(world);
                if (builder != null || player != null) {
                    Caravan caravan = builder.create(world);
                    if (MTGameStages.canSpawnCaravan(player, caravan)) {
                        BlockPos pos = EntityUtil.generatePosition(world, player.getPosition(), ConfigHandler.maxRadius, ConfigHandler.minRadius);
                        caravan.spawn(pos, player);
                        if (ConfigHandler.debug)
                            FMLLog.warning("Successfully spawning a caravan at %s! Going to %s", pos, player);
                        player.sendStatusMessage(new TextComponentTranslation("text.arriving"), true);
                    }
                }
            }

        }
    }

    @Mod.EventBusSubscriber(modid = CaravansMod.MODID)
    @Config(modid = CaravansMod.MODID, name = "caravans/caravans")
    public static class ConfigHandler {

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

        @Config.Comment("Trade Experience")
        public static int experience = 3;

        @SubscribeEvent
        public static void onConfigChange(ConfigChangedEvent event) {
            if (event.getModID().equals(CaravansMod.MODID)) {
                ConfigManager.sync(CaravansMod.MODID, Config.Type.INSTANCE);
            }
        }
    }
}

