package primetoxinz.caravans.client.gui;

import net.minecraftforge.items.ItemStackHandler;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.common.ItemTrade;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class InventoryMerchant {
    private ItemStackHandler input, output;

    public InventoryMerchant(Merchant merchant) {
        if (merchant != null) {
            int size = merchant.getTrades().size();
            this.input = new ItemStackHandler(size);
            this.output = new ItemStackHandler(size);
            for (int i = 0; i < size; i++) {
                ITrade itrade = merchant.getTrades().get(i);
                if(itrade instanceof ItemTrade) {
                    ItemTrade trade = (ItemTrade) itrade;
                    input.setStackInSlot(i, trade.getInput().copy());
                    output.setStackInSlot(i, trade.getOutput().copy());
                }
            }
        }
    }

    public ItemStackHandler getInput() {
        return input;
    }

    public ItemStackHandler getOutput() {
        return output;
    }
}
