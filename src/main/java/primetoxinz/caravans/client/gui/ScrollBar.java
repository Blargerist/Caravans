package primetoxinz.caravans.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.MathHelper;

import static primetoxinz.caravans.client.gui.GuiMerchant.CARAVAN_LOC;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class ScrollBar {

    private GuiBase parent;

    protected double max;
    protected int current;

    private int UI_HEIGHT = 108, BAR_HEIGHT = 15;

    public ScrollBar(int max, GuiBase parent) {
        this.parent = parent;
        this.current = 0;
        this.max = max;
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        int texX = needed() ? 232 : 244;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        parent.parent.mc.getTextureManager().bindTexture(CARAVAN_LOC);
        parent.parent.drawTexturedModalRect(x + parent.left(), parent.top() + y + getPosition(), texX, 0, 12, BAR_HEIGHT);
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
