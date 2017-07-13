package primetoxinz.caravans.client.gui;

import net.minecraftforge.items.ItemStackHandler;
import primetoxinz.caravans.common.ItemTrade;

import java.util.List;

/**
 * Created by primetoxinz on 7/12/17.
 */
public class InventoryMerchantItem extends InventoryMerchant<ItemTrade> implements InventoryMerchant.IInput, InventoryMerchant.IOutput {
    protected ItemStackHandler input, output;

    public InventoryMerchantItem(List<ItemTrade> trades) {
        super(trades);
        this.input = new ItemStackHandler(getSize());
        this.output = new ItemStackHandler(getSize());
        for (int i = 0; i < getSize(); i++) {
            ItemTrade trade = trades.get(i);
            input.setStackInSlot(i, trade.getInput().copy());
            output.setStackInSlot(i, trade.getOutput().copy());
        }
    }

    public ItemStackHandler getInput() {
        return input;
    }

    public ItemStackHandler getOutput() {
        return output;
    }

}
