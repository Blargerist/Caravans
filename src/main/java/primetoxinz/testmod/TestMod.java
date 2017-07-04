package primetoxinz.testmod;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import primetoxinz.testmod.network.MessageSync;
import primetoxinz.testmod.network.NetworkHandler;
import primetoxinz.testmod.proxy.CommonProxy;

/**
 * Created by primetoxinz on 7/3/17.
 */
@Mod.EventBusSubscriber
@Mod(modid = TestMod.MODID, name = TestMod.MODID)
public class TestMod {
    public static final String MODID = "test";


    @SidedProxy(modId = MODID, clientSide = "primetoxinz.testmod.proxy.ClientProxy", serverSide = "primetoxinz.testmod.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(owner = MODID)
    public static TestMod INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        registerEntity(EntityTest.class, "test", 64, 1, false);
        NetworkHandler.register(MessageSync.class, Side.CLIENT);
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
}
