package primetoxinz.caravans.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.client.*;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.types.*;

import java.util.UUID;

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
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeperCaravaneer.class, RenderCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderCaravaneer.class, RenderSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHumanCaravaneer.class, RenderHuman::new);
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


    @SideOnly(Side.CLIENT)
    @Override
    public void syncLeashEntity(int id, int attachID) {
        World world = Minecraft.getMinecraft().world;
        EntityLivingBase attach = (EntityLivingBase) world.getEntityByID(attachID);
        EntityLiving livingBase = (EntityLiving) world.getEntityByID(id);
        livingBase.setLeashedToEntity(attach, true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void syncLeashPlayer(int id, String uuid) {
        World world = Minecraft.getMinecraft().world;
        EntityPlayer player = world.getPlayerEntityByUUID(UUID.fromString(uuid));
        EntityLiving livingBase = (EntityLiving) world.getEntityByID(id);
        livingBase.setLeashedToEntity(player, true);
    }

}
