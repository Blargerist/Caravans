package primetoxinz.caravans.compat;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.CaravanAPI;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.MerchantBuilder;
import primetoxinz.caravans.common.EntityTrade;
import primetoxinz.caravans.common.ItemEntityTrade;
import primetoxinz.caravans.common.ItemTrade;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static primetoxinz.caravans.common.entity.EntityUtil.getEntity;

/**
 * Created by primetoxinz on 7/9/17.
 */
@ZenClass("mod.caravans.Merchant")
public class MTMerchant {

    @ZenMethod()
    public static void registerMerchant(String name, IItemStack icon) {
        ItemStack i = toStack(icon);
        MineTweakerAPI.apply(new RegisterMerchant(name, i));
    }

    public static class RegisterMerchant implements IUndoableAction {
        private MerchantBuilder merchant;

        protected RegisterMerchant(String name, ItemStack icon) {
            this.merchant = new MerchantBuilder(new ResourceLocation(CaravansMod.MODID, name), icon);
        }

        @Override
        public boolean canUndo() {
            return true;
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

        @Override
        public String describe() {
            return String.format("Registering MerchantBuilder: %s ", merchant);
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


    @ZenMethod
    public static void addEntityTrade(String merchant, String inputEntityClass, String outputEntityClass) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        Class<? extends EntityLiving> input = getEntity(inputEntityClass);
        Class<? extends EntityLiving> output = getEntity(outputEntityClass);
        EntityTrade trade = new EntityTrade(input, output);
        if (m != null) {
            MineTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    @ZenMethod
    public static void addItemEntityTrade(String merchant, IItemStack input, String entityClass) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        ItemStack in = toStack(input);
        Class<? extends EntityLiving> output = getEntity(entityClass);
        ItemEntityTrade trade = new ItemEntityTrade(in, output);
        if (m != null) {
            MineTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    @ZenMethod
    public static void addTrade(String merchant, IItemStack input, IItemStack output, int min, int max) {
        MerchantBuilder m = MTCompat.getMerchant(merchant);
        ItemStack in = toStack(input);
        ItemStack out = toStack(output);
        ItemTrade trade = new ItemTrade(in, out, min, max);
        if (m != null) {
            MineTweakerAPI.apply(new AddTrade(m, trade));
        }
    }

    public static class AddTrade implements IUndoableAction {

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
        public boolean canUndo() {
            return false;
        }

        @Override
        public void undo() {

        }

        @Override
        public String describe() {
            return String.format("Adding ItemTrade: %s to MerchantBuilder: %s ", trade, merchant);
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

    public static ItemStack toStack(IItemStack iStack) {
        if (iStack == null) {
            return null;
        } else {
            Object internal = iStack.getInternal();
            if (!(internal instanceof ItemStack)) {
                MineTweakerAPI.logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }
}
