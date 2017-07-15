package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.ConfigHandler;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AIHangOut extends AIAction {
    public AIHangOut(EntityCaravaneer entity) {
        super(entity);
    }

    @Override
    public boolean shouldExecute() {
        return !isFinished();
    }

    @Override
    public void updateTask() {
        super.updateTask();
        if (entity.stay == 0) {
            World world = entity.world;
            if(entity.target != null) {
                EntityPlayer player = world.getPlayerEntityByUUID(entity.target);
                if (player != null && player.getGameProfile().getName().equalsIgnoreCase("darkosto")) {
                    player.sendStatusMessage(new TextComponentString("Happy Birthday Darkosto"), false);
                    player.playSound(CaravansMod.SPECIAL,1.0f,1.0f);
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        if (ConfigHandler.hangoutTicks == -1)
            return false;
        return entity.stay >= ConfigHandler.hangoutTicks;
    }
}
