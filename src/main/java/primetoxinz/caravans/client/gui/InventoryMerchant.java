package primetoxinz.caravans.client.gui;

import net.minecraftforge.items.ItemStackHandler;
import primetoxinz.caravans.api.ITrade;

import java.util.List;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class InventoryMerchant<T extends ITrade> {

    protected List<T> trades;

    public InventoryMerchant(List<T> trades) {
        this.trades = trades;
    }


    public int getSize() {
        return trades.size();
    }

    public interface IInput {
        ItemStackHandler getInput();
    }


    public interface IOutput {
        ItemStackHandler getOutput();
    }

}
