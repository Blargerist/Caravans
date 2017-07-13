package primetoxinz.caravans.proxy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.common.ItemUtils;
import primetoxinz.caravans.common.entity.EntityUtil;

/**
 * Created by primetoxinz on 7/3/17.
 */

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void syncCaravaneer(int id, NBTTagCompound caravan) {

    }


    public void buyEntityTrade(MessageContext context, ItemStack input, String inputEntity, String outputEntity) {
        EntityPlayerMP player = context.getServerHandler().player;
        if (input.isEmpty()) {
            Class i = EntityUtil.getEntity(inputEntity);
            Class o = EntityUtil.getEntity(outputEntity);
            EntityLiving leashed = EntityUtil.getLeashed(i,player.world,player);

            if(leashed != null) {
                leashed.setDead();
                EntityLiving living = EntityUtil.setPosition(EntityUtil.createEntity(o, player.world), player.getPosition());
                living.setLeashedToEntity(player, false);
                player.world.spawnEntity(living);
            }

        } else {
            if (ItemUtils.hasItemStack(player, input)) {
                ItemUtils.takeItemStack(player, input, input.getCount());
                Class o = EntityUtil.getEntity(outputEntity);
                EntityLiving living = EntityUtil.setPosition(EntityUtil.createEntity(o, player.world), player.getPosition());
                living.setLeashedToEntity(player, false);
                player.world.spawnEntity(living);
            }
        }
    }
}
