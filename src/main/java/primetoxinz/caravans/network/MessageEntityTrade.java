package primetoxinz.caravans.network;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;

/**
 * Created by primetoxinz on 7/12/17.
 */
public class MessageEntityTrade extends NetworkMessage {

    public ItemStack input = ItemStack.EMPTY;

    public String inputEnity = "", outputEntity = "";

    public MessageEntityTrade() {
    }

    public MessageEntityTrade(String inputEnity, String outputEntity) {
        this.inputEnity = inputEnity;
        this.outputEntity = outputEntity;
    }

    public MessageEntityTrade(ItemStack input, String outputEntity) {
        this.input = input;
        this.outputEntity = outputEntity;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        CaravansMod.proxy.buyEntityTrade(context,input,inputEnity,outputEntity);
        return super.handleMessage(context);
    }
}
