package primetoxinz.caravans.client.gui.slot;

import primetoxinz.caravans.client.gui.InventoryMerchant;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class SlotOutput extends SlotBase {
    public SlotOutput(InventoryMerchant merchant, int index, int xPosition, int yPosition) {
        super(merchant.getOutput(), index, xPosition, yPosition);
    }
}
