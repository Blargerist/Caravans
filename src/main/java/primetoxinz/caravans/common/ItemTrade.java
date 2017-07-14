package primetoxinz.caravans.common;

import minetweaker.util.IntegerRange;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.Range;
import primetoxinz.caravans.api.ITrade;
import stanhebben.zenscript.value.IntRange;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class ItemTrade implements ITrade {
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;
    private int stock;
    private IntegerRange range;

    public ItemTrade(ItemStack input, ItemStack output, int min, int max) {
        this.input = input;
        this.output = output;
        this.range = new IntegerRange(min, max);
    }


    public ItemTrade(NBTTagCompound tag) {
        this.input = new ItemStack((NBTTagCompound) tag.getTag("input"));
        this.output = new ItemStack((NBTTagCompound) tag.getTag("output"));
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
        return String.format("%s -> %s : %s", getInput(), getOutput(), getStock());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "item");
        tag.setTag("input", input.serializeNBT());
        tag.setTag("output", output.serializeNBT());
        tag.setInteger("stock", stock);
        return tag;
    }

    @Override
    public ItemTrade create() {
        this.stock = range.getRandom();
        return this;
    }


}
