package primetoxinz.caravans.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.ICaravaneer;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class MessageCaravan extends NetworkMessage {
    public NBTTagCompound caravan;
    public int id;

    public MessageCaravan() {
    }

    public MessageCaravan(ICaravaneer caravaner) {
        this.id = caravaner.getID();
        this.caravan = caravaner.getCaravan().serializeNBT();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        CaravansMod.proxy.syncCaravaneer(id, caravan);
        return null;
    }
}
