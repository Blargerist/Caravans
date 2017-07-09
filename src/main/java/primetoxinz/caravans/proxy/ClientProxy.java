package primetoxinz.caravans.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.client.RenderCaravaneer;
import primetoxinz.caravans.client.RenderSkeleton;
import primetoxinz.caravans.client.RenderVillager;
import primetoxinz.caravans.client.RenderZombie;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.types.EntitySkeletonCaravaneer;
import primetoxinz.caravans.common.entity.types.EntityVillagerCaravaneer;
import primetoxinz.caravans.common.entity.types.EntityZombieCaravaneer;

/**
 * Created by primetoxinz on 7/1/17.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityVillagerCaravaneer.class, RenderVillager::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieCaravaneer.class, RenderZombie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonCaravaneer.class, RenderSkeleton::new);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void syncCaravaneer(int id, NBTTagCompound caravan) {
        World world = Minecraft.getMinecraft().world;
        Entity entity = world.getEntityByID(id);
        if (entity instanceof EntityCaravaneer) {
            EntityCaravaneer c = ((EntityCaravaneer) entity);
            c.setCaravan(new Caravan(world, caravan));
        }
    }


}
