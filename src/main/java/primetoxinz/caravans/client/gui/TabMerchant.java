package primetoxinz.caravans.client.gui;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class TabMerchant extends GuiButton {

    public TabMerchant(int tab, int x, int y, String buttonText) {
        super(tab, x, y, buttonText);
    }

    public TabMerchant(int tab, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(tab, x, y, widthIn, heightIn, buttonText);
    }
}
