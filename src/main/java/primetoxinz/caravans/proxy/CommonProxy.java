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
import primetoxinz.caravans.api.ITradeEntity;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.common.trades.TradeEntity;
import primetoxinz.caravans.common.trades.TradeItemEntity;
import primetoxinz.caravans.common.ItemUtils;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.compat.IEntity;

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

        ITradeEntity trade = (ITradeEntity) ITrade.deserializeNBT(tradeTag);

        World world = context.getServerHandler().player.world;
        EntityPlayerMP player = context.getServerHandler().player;

        EntityCaravaneer caravaneer = (EntityCaravaneer) world.getEntityByID(id);
        if (!trade.isInStock())
            return;
        if (trade instanceof TradeItemEntity) {
            TradeItemEntity itemEntityTrade = (TradeItemEntity) trade;
            ItemStack input = itemEntityTrade.getInput();
            IEntity o = itemEntityTrade.getOutput();
            if (ItemUtils.hasItemStack(player, input) && giveLeashed(o, player)) {
                ItemUtils.takeItemStack(player, input, input.getCount());
                trade.onTrade(world, caravaneer);
            }
        } else if (trade instanceof TradeEntity) {
            TradeEntity entityTrade = (TradeEntity) trade;
            IEntity i = entityTrade.getInput();
            IEntity o = entityTrade.getOutput();
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
