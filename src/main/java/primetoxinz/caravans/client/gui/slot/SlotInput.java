package primetoxinz.caravans.client.gui.slot;

import primetoxinz.caravans.client.gui.InventoryMerchant;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class SlotInput extends SlotBase {
    public SlotInput(InventoryMerchant.IInput merchant, int index, int xPosition, int yPosition) {
        super(merchant.getInput(), index, xPosition, yPosition);
    }

}
