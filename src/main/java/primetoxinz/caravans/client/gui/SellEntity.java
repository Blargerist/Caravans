package primetoxinz.caravans.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityLiving;
import primetoxinz.caravans.api.IEntityTrade;
import primetoxinz.caravans.common.entity.EntityUtil;

import java.util.List;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class SellEntity extends GuiBase {
    private int index = 0;
    private Button next,prev;
    public SellEntity(GuiMerchant parent) {
        super(parent);
        next = new Button(0,100,0,32,20,">", () -> index++);
        prev = new Button(0,100,32,32,20,"<", () -> index--);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        next.drawButton(parent.mc,mouseX,mouseY);
        prev.drawButton(parent.mc,mouseX,mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(next.mousePressed(parent.mc,mouseX,mouseY)) {
            next.clicked();
        }
        if(prev.mousePressed(parent.mc,mouseX,mouseY)) {
            prev.clicked();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        List<IEntityTrade> trades = getContainer().merchant.getEntityTrades();
        if (!trades.isEmpty()) {
            EntityLiving entity = EntityUtil.createEntity(trades.get(index).getOutput(), getContainer().world);
            parent.drawEntityOnScreen(140, 100, 30, 140, parent.getGuiTop(), entity);
        }

    }
}
