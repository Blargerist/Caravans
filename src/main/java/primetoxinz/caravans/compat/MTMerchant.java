package primetoxinz.caravans.compat;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseListAddition;
import com.blamejared.mtlib.utils.BaseUndoable;
import com.google.common.collect.Lists;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.common.Trade;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 * Created by primetoxinz on 7/9/17.
 */
@ZenClass("mod.caravans.Merchant")
public class MTMerchant {

    @ZenMethod()
    public static void registerMerchant(String name, IItemStack icon) {
        ItemStack i = InputHelper.toStack(icon);
        MineTweakerAPI.apply(new RegisterMerchant(name, i));
    }

    public static class RegisterMerchant extends BaseUndoable {
        private Merchant merchant;

        protected RegisterMerchant(String name, ItemStack icon) {
            super("registerMerchant");
            this.merchant = new Merchant(new ResourceLocation(CaravansMod.MODID, name));
            merchant.setIcon(icon);
        }

        @Override
        public boolean canUndo() {
            return false;
        }


        @Override
        public void apply() {
            if (!CaravanAPI.MERCHANTS.containsKey(merchant.getRegistryName())) {
                GameRegistry.register(merchant);
            }
        }

        @Override
        public void undo() {

        }
    }

    @ZenMethod
    public static void addTrade(String merchant, IItemStack input, IItemStack output) {
        Merchant m = MTCompat.getMerchant(merchant);
        ItemStack in = InputHelper.toStack(input);
        ItemStack out = InputHelper.toStack(output);
        Trade trade = new Trade(in, out);
        if (m != null) {
            MineTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    public static class AddTrade extends BaseUndoable {

        private final Merchant merchant;
        private final Trade trade;

        public AddTrade(Merchant merchant, Trade trade) {
            super("addTrade");
            this.merchant = merchant;
            this.trade = trade;
        }

        @Override
        public void apply() {
            merchant.addTrade(trade);
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        public void undo() {

        }

    }

}
