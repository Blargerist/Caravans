package primetoxinz.caravans.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.capability.CapabilityCaravaner;
import primetoxinz.caravans.capability.ICaravaner;
import primetoxinz.caravans.client.RenderDonkey;
import primetoxinz.caravans.client.RenderTrader;
import primetoxinz.caravans.client.RenderZombie;
import primetoxinz.caravans.entity.EntityCaravanerDonkey;
import primetoxinz.caravans.entity.EntityCaravanerTrader;
import primetoxinz.caravans.entity.EntityCaravanerZombie;
import primetoxinz.caravans.network.MessageCaravan;
import primetoxinz.caravans.network.NetworkHandler;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravanerTrader.class, RenderTrader::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravanerZombie.class, RenderZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravanerDonkey.class, RenderDonkey::new);
    }


    @Override
    public void syncCaravaner(int id, String caravan) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(id);
        if (entity != null && entity.hasCapability(CapabilityCaravaner.CARAVANER_CAPABILITY, null)) {
            ICaravaner caravaner = entity.getCapability(CapabilityCaravaner.CARAVANER_CAPABILITY, null);
            caravaner.setCaravan(CaravanAPI.CARAVANS.getValue(new ResourceLocation(caravan)));
        }
    }


}
