package primetoxinz.caravans.client.gui.slot;

import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.InventoryMerchant;
import primetoxinz.caravans.common.trades.TradeItem;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class SlotOutput extends SlotBase {
    private int index;
    private final Merchant merchant;

    public SlotOutput(InventoryMerchant.IOutput inv, int index, int xPosition, int yPosition, Merchant merchant) {
        super(inv.getOutput(), index, xPosition, yPosition);
        this.index = index;
        this.merchant = merchant;
    }

    public TradeItem getTrade() {
        return merchant.getItemTrades().get(index);
    }

    public int getStock() {
        return getTrade().getStock();
    }
}
