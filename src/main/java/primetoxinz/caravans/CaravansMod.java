package primetoxinz.caravans;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.GuiHandler;
import primetoxinz.caravans.common.CommandCaravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.proxy.CommonProxy;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = CaravansMod.MODID)
@Mod(modid = CaravansMod.MODID, name = CaravansMod.NAME, version = CaravansMod.VERSION)
public class CaravansMod {
    public static final String MODID = "caravans";
    public static final String NAME = "Villager CaravansMod";
    public static final String VERSION = "{version}";


    @SidedProxy(modId = MODID, clientSide = "primetoxinz.caravans.proxy.ClientProxy", serverSide = "primetoxinz.caravans.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(owner = MODID)
    public static CaravansMod INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        registerEntity(EntityCaravaneer.class, "caravaner.trader", 256, 1, true);
        NetworkRegistry.INSTANCE.registerGuiHandler(CaravansMod.INSTANCE, new GuiHandler());
        NetworkHandler.register(MessageCaravan.class, Side.CLIENT);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CaravanAPI.init();
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

}

