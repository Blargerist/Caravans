package primetoxinz.caravans.compat;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.MerchantBuilder;
import primetoxinz.caravans.common.trades.TradeEntity;
import primetoxinz.caravans.common.trades.TradeItem;
import primetoxinz.caravans.common.trades.TradeItemEntity;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by primetoxinz on 7/9/17.
 */
@ZenClass("mod.caravans.Merchant")
public class MTMerchant {

    @ZenMethod
    public static IEntity createEntity(String classPath, @Optional String customInfo) {
        EntityTradeable entity = new EntityTradeable();
        entity.setEntityClassPath(classPath);
        entity.setCustomInfo(customInfo);
        return entity;
    }

    @ZenMethod
    public static void registerMerchant(String name, IItemStack icon) {
        ItemStack i = toStack(icon);
        CraftTweakerAPI.apply(new RegisterMerchant(name, i));
    }

    public static class RegisterMerchant implements IAction {
        private MerchantBuilder merchant;

        protected RegisterMerchant(String name, ItemStack icon) {
            this.merchant = new MerchantBuilder(new ResourceLocation(CaravansMod.MODID, name), icon);
        }

          @Override
        public void apply() {
            if (!CaravanAPI.MERCHANTS.containsKey(merchant.getRegistryName())) {
                CaravanAPI.MERCHANTS.register(merchant);
            }
        }


        @Override
        public String describe() {
            return String.format("Registering MerchantBuilder: %s ", merchant);
        }

    }


    @ZenMethod
    public static void addEntityTrade(String merchant, IEntity input, IEntity output) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        TradeEntity trade = new TradeEntity(input, output);
        if (m != null) {
            CraftTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    @ZenMethod
    public static void addItemEntityTrade(String merchant, IItemStack input, IEntity output) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        ItemStack in = toStack(input);
        TradeItemEntity trade = new TradeItemEntity(in, output);
        if (m != null) {
            CraftTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    @ZenMethod
    public static void addTrade(String merchant, IItemStack input, IItemStack output, int min, int max) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        ItemStack in = toStack(input);
        ItemStack out = toStack(output);
        TradeItem trade = new TradeItem(in, out, min, max);
        if (m != null) {
            CraftTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    public static class AddTrade implements IAction {

        private final MerchantBuilder merchant;
        private final ITrade trade;

        public AddTrade(MerchantBuilder merchant, ITrade trade) {
            this.merchant = merchant;
            this.trade = trade;
        }

        @Override
        public void apply() {
            merchant.addTrade(trade);
        }



        @Override
        public String describe() {
            return String.format("Adding TradeItem: %s to MerchantBuilder: %s ", trade, merchant);
        }

    }

    public static ItemStack toStack(IItemStack iStack) {
        if (iStack == null) {
            return null;
        } else {
            Object internal = iStack.getInternal();
            if (!(internal instanceof ItemStack)) {
                CraftTweakerAPI.logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }
}
