package primetoxinz.caravans.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.capability.ICaravaneer;
import primetoxinz.caravans.client.RenderCaravaneer;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneer.class, RenderCaravaneer::new);
//        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneerZombie.class, RenderZombie::new);
//        RenderingRegistry.registerEntityRenderingHandler(EntityCaravaneerDonkey.class, RenderDonkey::new);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void syncCaravaneer(int id, String caravan) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(id);
        if (entity instanceof ICaravaneer) {
            ICaravaneer caravaneer = (ICaravaneer) entity;
            caravaneer.setCaravan(CaravanAPI.CARAVANS.getValue(new ResourceLocation(caravan)));
        }
    }


}
