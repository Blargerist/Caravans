package primetoxinz.caravans.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.capability.CapabilityCaravaneer;
import primetoxinz.caravans.capability.ICaravaneer;
import primetoxinz.caravans.client.RenderDonkey;
import primetoxinz.caravans.client.RenderTrader;
import primetoxinz.caravans.client.RenderZombie;
import primetoxinz.caravans.common.entity.EntityCaravaneerDonkey;
import primetoxinz.caravans.common.entity.EntityCaravaneerTrader;
import primetoxinz.caravans.common.entity.EntityCaravaneerZombie;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneerTrader.class, RenderTrader::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneerZombie.class, RenderZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneerDonkey.class, RenderDonkey::new);
    }


    @Override
    public void syncCaravaner(int id, String caravan) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(id);
        if (entity != null && entity.hasCapability(CapabilityCaravaneer.CARAVANER_CAPABILITY, null)) {
            ICaravaneer caravaner = entity.getCapability(CapabilityCaravaneer.CARAVANER_CAPABILITY, null);
            caravaner.setCaravan(CaravanAPI.CARAVANS.getValue(new ResourceLocation(caravan)));
        }
    }


}
