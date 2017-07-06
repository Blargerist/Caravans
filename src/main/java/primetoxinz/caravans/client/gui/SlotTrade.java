package primetoxinz.caravans.client.gui;

import net.minecraft.item.ItemStack;
import primetoxinz.caravans.api.ITrade;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class SlotTrade implements ITrade{

    private ITrade trade;
    private int x, y;

    public SlotTrade(ITrade trade, int x, int y) {
        this.trade = trade;
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public ItemStack getInput() {
        return trade.getInput();
    }

    @Override
    public ItemStack getOutput() {
        return trade.getOutput();
    }
}
