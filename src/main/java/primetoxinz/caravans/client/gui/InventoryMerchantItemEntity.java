package primetoxinz.caravans.client.gui;

import net.minecraftforge.items.ItemStackHandler;
import primetoxinz.caravans.common.ItemEntityTrade;

import java.util.List;

/**
 * Created by primetoxinz on 7/12/17.
 */
public class InventoryMerchantItemEntity extends InventoryMerchant<ItemEntityTrade> implements InventoryMerchant.IInput {
    protected ItemStackHandler input;

    public InventoryMerchantItemEntity(List<ItemEntityTrade> trades) {
        super(trades);
        this.input = new ItemStackHandler(getSize());
        for (int i = 0; i < getSize(); i++) {
            ItemEntityTrade trade = trades.get(i);
            input.setStackInSlot(i, trade.getInput().copy());
        }
    }

    public ItemStackHandler getInput() {
        return input;
    }

}
