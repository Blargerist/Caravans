package primetoxinz.testmod.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.testmod.ITest;
import primetoxinz.testmod.TestMod;

/**
 * Created by primetoxinz on 7/3/17.
 */
public class MessageSync extends NetworkMessage {
    public int working;
    public int id;

    public MessageSync() {
    }

    public MessageSync(ITest test) {
        this.id = test.getId();
        this.working = test.isWorking();
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        TestMod.proxy.sync(id, working);
        return null;
    }
}
