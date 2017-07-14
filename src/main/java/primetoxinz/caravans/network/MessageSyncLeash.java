package primetoxinz.caravans.network;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import primetoxinz.caravans.CaravansMod;

/**
 * Created by primetoxinz on 7/13/17.
 */
public class MessageSyncLeash extends NetworkMessage {

    public int entityId, attachId;
    public String playerUUID;

    public MessageSyncLeash() {
    }

    public MessageSyncLeash(EntityLiving entity, EntityLivingBase attach) {
        entityId = entity.getEntityId();
        if (attach instanceof EntityPlayer) {
            playerUUID = ((EntityPlayer) attach).getGameProfile().getId().toString();
            attachId = -1;
        } else {
            attachId = attach.getEntityId();
        }
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        if (attachId > -1) {
            CaravansMod.proxy.syncLeashEntity(entityId, attachId);
        } else {
            CaravansMod.proxy.syncLeashPlayer(entityId, playerUUID);
        }
        return super.handleMessage(context);
    }
}
