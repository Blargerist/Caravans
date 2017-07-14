package primetoxinz.caravans.proxy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.api.IEntityTrade;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.common.EntityTrade;
import primetoxinz.caravans.common.ItemEntityTrade;
import primetoxinz.caravans.common.ItemUtils;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import static primetoxinz.caravans.common.entity.EntityUtil.giveLeashed;
import static primetoxinz.caravans.common.entity.EntityUtil.takeLeashed;

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


    public void buyEntityTrade(MessageContext context, NBTTagCompound tradeTag, int id) {

        IEntityTrade trade = (IEntityTrade) ITrade.deserializeNBT(tradeTag);

        World world = context.getServerHandler().player.world;
        EntityPlayerMP player = context.getServerHandler().player;

        EntityCaravaneer caravaneer = (EntityCaravaneer) world.getEntityByID(id);
        if (!trade.isInStock())
            return;
        if (trade instanceof ItemEntityTrade) {
            ItemEntityTrade itemEntityTrade = (ItemEntityTrade) trade;
            ItemStack input = itemEntityTrade.getInput();
            Class<? extends EntityLiving> o = itemEntityTrade.getOutput();
            if (ItemUtils.hasItemStack(player, input) && giveLeashed(o, player)) {
                ItemUtils.takeItemStack(player, input, input.getCount());
                trade.onTrade(world, caravaneer);
            }
        } else if (trade instanceof EntityTrade) {
            EntityTrade entityTrade = (EntityTrade) trade;
            Class<? extends EntityLiving> i = entityTrade.getInput();
            Class<? extends EntityLiving> o = entityTrade.getOutput();
            if (takeLeashed(o, caravaneer) && takeLeashed(i, player)) {
                if(giveLeashed(o, player) && giveLeashed(i, caravaneer)) {
                    trade.onTrade(world, caravaneer);
                    caravaneer.sync();
                }
            }
        }
    }

    public void syncLeashEntity(int id, int attachID) {
    }

    public void syncLeashPlayer(int id, String uuid) {
    }
}
