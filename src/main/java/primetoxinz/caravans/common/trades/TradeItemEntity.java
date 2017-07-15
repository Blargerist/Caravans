package primetoxinz.caravans.common.trades;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import primetoxinz.caravans.api.ITradeEntity;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.compat.EntityTradeable;
import primetoxinz.caravans.compat.IEntity;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class TradeItemEntity implements ITradeEntity {
    private ItemStack input = ItemStack.EMPTY;
    private EntityTradeable output;
    private int stock;

    public TradeItemEntity(ItemStack input, IEntity output) {
        this.input = input;
        this.output = (EntityTradeable) output;
    }

    public TradeItemEntity(NBTTagCompound tag) {
        if (tag.hasKey("input"))
            input = new ItemStack(tag.getCompoundTag("input"));
        output = EntityUtil.deserializeIEntity(tag.getCompoundTag("output"));
        stock = tag.getInteger("stock");
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public IEntity getOutput() {
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
        tag.setString("type", "item_entity");
        if (!input.isEmpty())
            tag.setTag("input", input.serializeNBT());
        tag.setTag("output", EntityUtil.serializeIEntity(output));
        tag.setInteger("stock", stock);
        return tag;
    }


}
