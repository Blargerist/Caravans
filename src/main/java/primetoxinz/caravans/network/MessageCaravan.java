package primetoxinz.caravans.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class MessageCaravan extends NetworkMessage {
    public NBTTagCompound caravan;
    public int id;

    public MessageCaravan() {
    }

    public MessageCaravan(EntityCaravaneer caravaner) {
        this.id = caravaner.getEntityId();
        this.caravan = caravaner.getCaravan().serializeNBT();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        CaravansMod.proxy.syncCaravaneer(id, caravan);
        return null;
    }
}
