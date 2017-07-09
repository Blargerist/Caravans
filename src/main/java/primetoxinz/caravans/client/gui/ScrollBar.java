package primetoxinz.caravans.client.gui;

import net.minecraft.util.math.MathHelper;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class ScrollBar {

    private GuiMerchant parent;

    protected double max;
    protected int current;

    private int UI_HEIGHT = 108, BAR_HEIGHT = 15;

    public ScrollBar(int max, GuiMerchant parent) {
        this.parent = parent;
        this.current = 0;
        this.max = max;
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        int texX = needed() ? 232 : 244;
        parent.drawTexturedModalRect(x + parent.getGuiLeft(), parent.getGuiTop() + y + getPosition(), texX, 0, 12, BAR_HEIGHT);
    }


    public int getPosition() {
        if (max == 0)
            return 0;
        return Math.min(UI_HEIGHT - 2 - BAR_HEIGHT, (int) ((current / max) * (double) UI_HEIGHT));
    }

    public boolean needed() {
        return max > 6;
    }

    public void move(int input) {
        current = MathHelper.clamp(current + input, 0, (int) max - 1);
        parent.update();
    }


    public int getCurrent() {
        return current;
    }
}
