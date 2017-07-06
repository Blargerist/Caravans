package primetoxinz.caravans.client.gui;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by primetoxinz on 7/5/17.
 */
public class Button extends GuiButton {
    private Runnable press;

    public Button(int buttonId, int x, int y, String buttonText, Runnable press) {
        super(buttonId, x, y, buttonText);
        this.press = press;
    }

    public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Runnable press) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.press = press;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        press.run();
        super.mouseReleased(mouseX, mouseY);
    }

}
