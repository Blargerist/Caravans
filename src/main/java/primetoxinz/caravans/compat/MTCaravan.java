package primetoxinz.caravans.compat;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.CaravanBuilder;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import stanhebben.zenscript.annotations.Optional;
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
    public static void registerCaravan(String name, @Optional String gamestage) {
        MineTweakerAPI.apply(new RegisterCaravan(name, gamestage));
    }

    public static class RegisterCaravan implements IUndoableAction {
        private CaravanBuilder builder;

        protected RegisterCaravan(String name, String gamestage) {
            this.builder = new CaravanBuilder(new ResourceLocation(CaravansMod.MODID, name), EntityCaravaneer.class);
            if (gamestage != null) {
                builder.setStage(gamestage);
            }
        }

        @Override
        public boolean canUndo() {
            return false;
        }


        @Override
        public void apply() {
            if (!CaravanAPI.CARAVANS.containsKey(builder.getRegistryName())) {
                GameRegistry.register(builder);
            }
        }

        @Override
        public void undo() {

        }

        @Override
        public String describe() {
            return String.format("Registering Caravan: %s ", builder);
        }

        @Override
        public String describeUndo() {
            return null;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }


}
