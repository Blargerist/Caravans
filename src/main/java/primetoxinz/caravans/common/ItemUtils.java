package primetoxinz.caravans.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by primetoxinz on 7/8/17.
 */
public class ItemUtils {

    public static boolean matchesWildcard(ItemStack one, ItemStack two) {
        return one.getItem() == two.getItem() && (one.getMetadata() == OreDictionary.WILDCARD_VALUE || two.getMetadata() == OreDictionary.WILDCARD_VALUE);
    }

    public static boolean matches(ItemStack one, ItemStack two) {
        return matchesWildcard(one, two) || OreDictionary.itemMatches(one, two, true);
    }

    public static void takeItemStack(EntityPlayer player, ItemStack stack, int count) {
        IItemHandlerModifiable inv = (IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (matches(s, stack)) {
                inv.extractItem(i, count, false);
            }
        }
    }

    public static boolean hasItemStack(EntityPlayer player, ItemStack stack) {
        IItemHandlerModifiable inv = (IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack s = inv.getStackInSlot(i);
            if (matches(s, stack) && s.getCount() >= stack.getCount()) {
                return true;
            }
        }
        return false;
    }

}
