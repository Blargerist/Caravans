package primetoxinz.caravans.common.trades;

import minetweaker.util.IntegerRange;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import primetoxinz.caravans.api.ITrade;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class TradeItem implements ITrade {
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    private int stock;
    private IntegerRange range;

    public TradeItem(ItemStack input, ItemStack output, int min, int max) {
        this.input = input;
        this.output = output;
        this.range = new IntegerRange(min, max);
    }


    public TradeItem(NBTTagCompound tag) {
        if (tag.hasKey("input"))
            this.input = new ItemStack((NBTTagCompound) tag.getTag("input"));
        else
            this.input = ItemStack.EMPTY;
        if (tag.hasKey("output"))
            this.output = new ItemStack((NBTTagCompound) tag.getTag("output"));
        else
            this.output = ItemStack.EMPTY;
        this.stock = tag.getInteger("stock");
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
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s : %s\nItem", getInput(), getOutput(), getStock());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "item");
        if (!input.isEmpty())
            tag.setTag("input", input.serializeNBT());
        if (!output.isEmpty())
            tag.setTag("output", output.serializeNBT());
        tag.setInteger("stock", stock);
        return tag;
    }

    @Override
    public TradeItem create() {
        this.stock = range.getRandom();
        return this;
    }


}
