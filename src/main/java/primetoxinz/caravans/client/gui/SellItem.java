package primetoxinz.caravans.client.gui;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import primetoxinz.caravans.client.gui.slot.SlotBase;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.client.gui.slot.SlotOutput;

import java.io.IOException;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class SellItem extends GuiBase {


    protected ScrollBar scrollBar;
    private static final int SHOW_COUNT = 6;
    public SellItem(GuiMerchant parent) {
        super(parent);
        if (getContainer().merchant != null) {
            this.scrollBar = new ScrollBar(getContainer().getSize(), this);
        }
        update();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        scrollBar.draw(68, 28, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        update();
    }

    @Override
    public void handleMouseInput() throws IOException {
        int i = Mouse.getDWheel();
        if (i != 0 && scrollBar != null && scrollBar.needed()) {
            i = i > 0 ? -1 : 1;
            scrollBar.move(i);
            update();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void update() {
        int c = scrollBar.getCurrent();
        int x = 8, y = 28;
        for (Slot slot : getContainer().inventorySlots) {
            if (slot instanceof SlotBase)
                ((SlotBase) slot).setEnabled(false);
        }
        for (int i = c; i < Math.min(getContainer().inputs.size(), c + SHOW_COUNT); i++) {
            SlotInput in = getContainer().inputs.get(i);
            SlotOutput out = getContainer().outputs.get(i);
            parent.drawSlot(out, x + 36, y);
            parent.drawSlot(in, x, y);
            y += 18;
        }
    }

}