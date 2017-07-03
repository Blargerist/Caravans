package primetoxinz.caravans.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.Caravans;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.capability.ICaravaner;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class MessageCaravan extends NetworkMessage {
    public String caravan;
    public int id;

    public MessageCaravan() {
    }

    public MessageCaravan(ICaravaner caravaner) {
        id = caravaner.getID();
        this.caravan = CaravanAPI.basic.getRegistryName().toString();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        Caravans.proxy.syncCaravaner(id, caravan);
        return null;
    }
}
