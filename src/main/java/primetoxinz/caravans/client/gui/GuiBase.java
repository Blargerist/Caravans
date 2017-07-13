package primetoxinz.caravans.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class GuiBase extends Gui {

    protected GuiMerchant parent;
    protected ContainerMerchant container;

    public GuiBase(GuiMerchant parent) {
        this.parent = parent;
        this.container = parent.container;
    }

    public boolean isEnabled() {
        return true;
    }

    public void init() {

    }

    public int left() {
        return parent.getGuiLeft();
    }

    public int top() {
        return parent.getGuiTop();
    }

    public Minecraft mc() {
        return parent.mc;
    }

    public <T extends GuiButton> T addButton(T button) {
        return parent.addButton(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        update();
    }

    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    public void update() {

    }

    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void handleMouseInput() throws IOException {

    }
}
