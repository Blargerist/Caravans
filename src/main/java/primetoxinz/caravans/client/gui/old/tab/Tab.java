package primetoxinz.caravans.client.gui.old.tab;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import primetoxinz.caravans.api.Merchant;

import java.awt.Rectangle;


/**
 * Created by primetoxinz on 7/6/17.
 */
public class Tab implements IDrawable {
    private ItemStack icon;
    private boolean selected;
    private String unlocalizedName;

    private IDrawable drawable;
    private GuiCaravan parent;

    private int x, y;

    public Tab(GuiCaravan parent, Merchant merchant) {
        this.parent = parent;
        this.icon = merchant.getIcon();
        this.unlocalizedName = merchant.getName();
        this.drawable = new TradeMenu(parent, merchant);
    }

    public ItemStack getIcon() {
        return icon;
    }

    public String getLocalizedName() {
        return I18n.format(unlocalizedName);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;
        if (mouseOver(mouseX, mouseY)) {
            parent.drawHoveringText(getLocalizedName(), x, y);
        }

        int offset = selected ? 43 : 15;
        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        parent.mc.getTextureManager().bindTexture(parent.CARAVAN_LOC);
        parent.drawTexturedModalRect(x, y, 224, offset, 32, 28);
        parent.drawItemStack(getIcon(), x + 6, y + 6, "");

        if (selected) {
            drawable.draw(8, 10, mouseX, mouseY);
        }
    }

    public boolean mouseOver(int mouseX, int mouseY) {
        return new Rectangle(this.x, this.y, 32, 32).contains(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.drawable.mouseReleased(mouseX, mouseY, state);
    }
}
