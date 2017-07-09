package primetoxinz.caravans;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
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
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.GuiHandler;
import primetoxinz.caravans.common.CommandCaravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.compat.MTCompat;
import primetoxinz.caravans.compat.MTGameStages;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.proxy.CommonProxy;

import java.io.File;
import java.util.Random;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
@Mod(modid = CaravansMod.MODID, name = CaravansMod.NAME, version = CaravansMod.VERSION)
public class CaravansMod {
    public static final String MODID = "caravans";
    public static final String NAME = "Caravans";
    public static final String VERSION = "{version}";


    @SidedProxy(modId = MODID, clientSide = "primetoxinz.caravans.proxy.ClientProxy", serverSide = "primetoxinz.caravans.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(owner = MODID)
    public static CaravansMod INSTANCE;


    public File caravansFolder;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerEntity(EntityCaravaneer.class, "caravaner.trader", 256, 1, true);
        NetworkRegistry.INSTANCE.registerGuiHandler(CaravansMod.INSTANCE, new GuiHandler());
        NetworkHandler.register(MessageCaravan.class, Side.CLIENT);
        proxy.preInit(event);
        caravansFolder = new File(event.getModConfigurationDirectory(), CaravansMod.MODID);
        if (!caravansFolder.exists())
            caravansFolder.mkdirs();
        if (Loader.isModLoaded("crafttweaker")) {
            MTCompat.preInit();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandCaravan());
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

        new RegistryBuilder<Merchant>()
                .setType(Merchant.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .setName(new ResourceLocation(CaravansMod.MODID, "merchants")).create();
    }


    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (!ConfigHandler.randomlySpawn)
            return;
        World world = event.world;
        Random rand = world.rand;
        if (world.playerEntities.isEmpty())
            return;
        if (event.phase == TickEvent.Phase.END && rand.nextInt(ConfigHandler.spawnChance) == 0) {
            CaravanBuilder builder = CaravanAPI.getRandomCaravan(world);
            EntityPlayer player = EntityUtil.getRandomPlayer(world);
            if (builder != null || player != null) {
                Caravan caravan = builder.create(world);
                if (MTGameStages.canSpawnCaravan(player, caravan)) {
                    BlockPos pos = EntityUtil.generatePosition(world, player.getPosition(), ConfigHandler.maxRadius, ConfigHandler.minRadius);
                    caravan.spawn(pos, player);
                    player.sendStatusMessage(new TextComponentTranslation("text.arriving"), true);
                }
            }
        }
    }
}

