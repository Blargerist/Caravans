package primetoxinz.caravans.client.gui.old.tab;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.ScrollBar;
import primetoxinz.caravans.common.entity.EntityUtil;

import java.awt.*;
import java.util.List;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class TradeMenu implements IDrawable {
    private List<ITrade> trades;
    private GuiCaravan parent;
    private ScrollBar scrollBar;
    private int x, y;

    private List<TradeRender> currentTrades = Lists.newArrayList();

    public TradeMenu(GuiCaravan parent, Merchant merchant) {
        this.parent = parent;
        this.trades = merchant.getTrades();
        scrollBar = new ScrollBar(trades.size(), parent);
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        if (parent.scrollBar != scrollBar)
            parent.scrollBar = scrollBar;

        int y1 = y + parent.getGuiTop();
        int s = scrollBar.current, e = Math.min(trades.size(), s + 6);
        List<TradeRender> currentTrades = Lists.newArrayList();
        for (ITrade trade : trades.subList(s, e)) {
            TradeRender render = new TradeRender(parent, x + parent.getGuiLeft(), y1 += 18, trade);
            render.draw(0, 0, mouseX, mouseY);
            currentTrades.add(render);
        }
        this.currentTrades = currentTrades;
        scrollBar.draw(parent.getGuiLeft() + 68, parent.getGuiTop() + 28, mouseX, mouseY);

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            for (TradeRender t : currentTrades) {
                if (t.isMouseOver(1, mouseX, mouseY)) {
                    ItemStack output = t.trade.getOutput();
                    ItemStack input = t.trade.getInput();
                    if (EntityUtil.hasItemStack(parent.player, input)) {
                        EntityUtil.takeItemStack(parent.player, input, input.getCount());
                        ItemHandlerHelper.giveItemToPlayer(parent.player, output);
                    }
                    return;
                }

            }
        }
    }

    public class TradeRender implements IDrawable {
        private GuiCaravan parent;
        private int x, y;
        private ITrade trade;

        public TradeRender(GuiCaravan parent, int x, int y, ITrade trade) {
            this.parent = parent;
            this.x = x;
            this.y = y;
            this.trade = trade;
        }

        protected boolean isMouseOver(int slot, int x, int y) {
            return new Rectangle(this.x + 36, this.y, 18, 18).contains(x, y);
        }

        @Override
        public void draw(int x, int y, int mouseX, int mouseY) {
            parent.drawItemStack(trade.getInput(), this.x, this.y, "");
            parent.drawItemStack(trade.getOutput(), this.x + 36, this.y, "");
        }
    }


}
