package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import primetoxinz.caravans.api.ITrade;
import primetoxinz.caravans.api.Merchant;

import java.awt.*;
import java.util.List;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class MerchantTab {

    Merchant merchant;
    List<SlotTrade> slots = Lists.newArrayList();
    private int tabX, tabY;

    public MerchantTab(Merchant merchant, int x, int y) {
        this.merchant = merchant;
        if (merchant != null && merchant.getTrades() != null) {
            for (ITrade trade : merchant.getTrades()) {
                slots.add(new SlotTrade(trade, x, y += 18));
            }
        }
    }

    public void setTabPos(int x, int y) {
        this.tabX = x;
        this.tabY = y;
    }

    public boolean isClicked(int x, int y) {

        return new Rectangle(tabX, tabY, 28, 28).contains(x, y);
    }


}
