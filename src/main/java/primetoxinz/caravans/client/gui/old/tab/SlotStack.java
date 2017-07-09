package primetoxinz.caravans.client.gui.old.tab;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class SlotStack extends Slot {
    private ItemStack stack;

    public SlotStack(ItemStack stack, int xPosition, int yPosition) {
        super(null, -1, xPosition, yPosition);
        this.stack = stack;
    }

    @Override
    public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
        //NO-OP
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        return ItemStack.EMPTY;
    }

    @Override
    public void onSlotChanged() {
        //NO-OP
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void putStack(ItemStack stack) {
        //NO-OP
    }

    @Override
    public int getSlotStackLimit() {
        return getStack().getMaxStackSize();
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn) {
        return true;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return stack;
    }
}
