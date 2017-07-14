package primetoxinz.caravans.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.IEntityTrade;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/12/17.
 */
public class MessageEntityTrade extends NetworkMessage {

    public NBTTagCompound trade;
    public int id;

    public MessageEntityTrade() {
    }

    public MessageEntityTrade(IEntityTrade trade, EntityCaravaneer caravaneer) {
        this.trade = trade.serializeNBT();
        this.id = caravaneer.getEntityId();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        CaravansMod.proxy.buyEntityTrade(context, trade, id);
        return super.handleMessage(context);
    }
}
