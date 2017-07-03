package primetoxinz.caravans;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
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
import primetoxinz.caravans.api.ICaravan;
import primetoxinz.caravans.capability.CapabilityCaravaner;
import primetoxinz.caravans.capability.ICaravaner;
import primetoxinz.caravans.entity.EntityCaravanerDonkey;
import primetoxinz.caravans.entity.EntityCaravanerTrader;
import primetoxinz.caravans.entity.EntityCaravanerZombie;
import primetoxinz.caravans.gui.GuiHandler;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;
import primetoxinz.caravans.proxy.CommonProxy;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(modid = Caravans.MODID)
@Mod(modid = Caravans.MODID, name = Caravans.NAME, version = Caravans.VERSION)
public class Caravans {
    public static final String MODID = "caravans";
    public static final String NAME = "Villager Caravans";
    public static final String VERSION = "{version}";

    @Mod.Instance(owner = MODID)
    public static Caravans INSTANCE;

    @SidedProxy(clientSide = "primetoxinz.caravans.proxy.ClientProxy", serverSide = "primetoxinz.caravans.proxy.ServerProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityCaravaner.register();

        proxy.preInit(event);

        registerEntity(EntityCaravanerZombie.class, "caravaner.zombie", 64, 1, true);
        registerEntity(EntityCaravanerTrader.class, "caravaner.trader", 64, 1, true);
        registerEntity(EntityCaravanerDonkey.class, "caravaner.donkey", 64, 1, true);

        NetworkRegistry.INSTANCE.registerGuiHandler(Caravans.INSTANCE, new GuiHandler());
        NetworkHandler.register(MessageCaravan.class, Side.CLIENT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        CaravanAPI.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
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
        new RegistryBuilder<ICaravan>()
                .setType(ICaravan.class)
                .setName(new ResourceLocation(Caravans.MODID, "caravans")).create();

    }
}

