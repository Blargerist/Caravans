package primetoxinz.caravans.common;

import net.minecraft.item.ItemStack;
import primetoxinz.caravans.api.ITrade;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class Trade implements ITrade {
    ItemStack input, output;

    public Trade(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", getInput(), getOutput());
    }
}
