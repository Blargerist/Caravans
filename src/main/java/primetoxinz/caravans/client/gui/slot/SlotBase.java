package primetoxinz.caravans.client.gui.slot;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by primetoxinz on 7/8/17.
 */
public class SlotBase extends SlotItemHandler {
    private boolean enabled;

    public SlotBase(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean canBeHovered() {
        return enabled;
    }

    public SlotBase setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
