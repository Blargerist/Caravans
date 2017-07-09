package primetoxinz.caravans.client.gui.old.tab;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class Button implements IDrawable {
    private GuiCaravan parent;
    private int x, y, w, h, texX, texY;
    private int state;
    private Runnable click;

    public Button(GuiCaravan parent, int x, int y, int w, int h, int texX, int texY, int state, Runnable click) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.texX = texX;
        this.texY = texY;
        this.state = state;
        this.click = click;
    }

    private boolean clicked(int x, int y) {
        return new Rectangle(this.x+parent.getGuiLeft(), this.y+parent.getGuiTop(), w, h).contains(x, y);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.state == state && clicked(mouseX, mouseY)) {
            click.run();
        }
    }

    @Override
    public void draw(int x, int y, int mouseX, int mouseY) {
        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        parent.mc.getTextureManager().bindTexture(parent.CARAVAN_LOC);
        parent.drawTexturedModalRect(this.x + x, this.y + y, texX, texY, w, h);
    }
}
