package primetoxinz.caravans.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.capability.ICaravaneer;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class MessageCaravan extends NetworkMessage {
    public String caravan;
    public int id;

    public MessageCaravan() {
    }

    public MessageCaravan(ICaravaneer caravaner) {
        id = caravaner.getID();
        this.caravan = caravaner.getCaravan().toString();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        CaravansMod.proxy.syncCaravaneer(id, caravan);
        return null;
    }
}
