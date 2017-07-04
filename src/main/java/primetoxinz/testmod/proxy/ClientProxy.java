package primetoxinz.testmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import primetoxinz.testmod.EntityTest;
import primetoxinz.testmod.ITest;

/**
 * Created by primetoxinz on 7/1/17.
 */

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, RenderZombie::new);
    }

    @Override
    public void sync(int id, int working) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(id);
        if (entity != null && entity instanceof ITest) {
            ITest test = (ITest) entity;
            test.setWorking(working);
        }
    }
}
