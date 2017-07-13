package primetoxinz.caravans.client.gui;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class Button extends GuiButton {

    private Runnable runnable;

    public Button(int buttonId, int x, int y, int w, int h, String buttonText, Runnable runnable) {
        super(buttonId, x, y, w, h, buttonText);
        this.runnable = runnable;

    }

    @Override
    public boolean isMouseOver() {
        return super.isMouseOver();
    }

    public void clicked() {
        runnable.run();
    }


}
