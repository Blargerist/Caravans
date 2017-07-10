package primetoxinz.caravans.client.gui;

import java.io.IOException;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class GuiBase {

    protected GuiMerchant parent;

    public GuiBase(GuiMerchant parent) {
        this.parent = parent;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    public void handleMouseInput() throws IOException {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void update() {

    }

    public ContainerMerchant getContainer() {
        return parent.container;
    }

    public int getGuiLeft() {
        return parent.getGuiLeft();
    }

    public int getGuiTop() {
        return parent.getGuiTop();
    }
}
