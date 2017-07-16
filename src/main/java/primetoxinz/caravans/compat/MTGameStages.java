package primetoxinz.caravans.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import primetoxinz.caravans.api.Caravan;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class MTGameStages {
    public static boolean canSpawnCaravan(EntityPlayer player, Caravan caravan) {
        if (!Loader.isModLoaded("gamestages"))
            return true;
        //TODO GameStages
//        if (caravan.getGameStage() != null)
//            return PlayerDataHandler.getStageData(player).hasUnlockedStage(caravan.getGameStage());
        return true;
    }
}
