package primetoxinz.caravans;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.gson.stream.JsonReader;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
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

import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
@Mod(modid = CaravansMod.MODID, name = CaravansMod.NAME, version = CaravansMod.VERSION, dependencies = CaravansMod.DEP)
public class CaravansMod {
    public static final String MODID = "caravans";
    public static final String NAME = "Caravans";
    public static final String VERSION = "{version}";
    public static final String DEP = "after:crafttweaker";


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
        if (world.playerEntities.isEmpty())
            return;
        if (event.phase == TickEvent.Phase.END && (world.getWorldTime() % 24000) == 4000) {
            double random = rand.nextDouble();
            double chance = ConfigHandler.spawnPercent / 100d;
            if (random < chance) {
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
}

