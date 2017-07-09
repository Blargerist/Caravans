package primetoxinz.caravans.compat;

import com.blamejared.mtlib.utils.BaseUndoable;
import minetweaker.MineTweakerAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by primetoxinz on 7/9/17.
 */
@ZenClass("mod.caravans.Caravan")
public class MTCaravan {

    //TODO removeTrade removeMerchant addCaravan removeCaravan addCaravanFolllower

    @ZenMethod
    public static void addFollower(String caravan, String merchant) {
        CaravanBuilder builder = MTCompat.getCaravan(caravan);
        Merchant follower = MTCompat.getMerchant(merchant);
        if (builder != null && follower != null) {
            builder.addFollower(EntityCaravaneer.class, follower);
        }
    }

    @ZenMethod
    public static void registerCaravan(String name) {
        MineTweakerAPI.apply(new RegisterCaravan(name));
    }

    public static class RegisterCaravan extends BaseUndoable {
        private CaravanBuilder caravan;

        protected RegisterCaravan(String name) {
            super("registerCaravan");
            this.caravan = new CaravanBuilder(new ResourceLocation(CaravansMod.MODID, name), EntityCaravaneer.class);
        }

        @Override
        public boolean canUndo() {
            return false;
        }


        @Override
        public void apply() {
            if (!CaravanAPI.CARAVANS.containsKey(caravan.getRegistryName())) {
                GameRegistry.register(caravan);
            }
        }

        @Override
        public void undo() {

        }
    }


}
