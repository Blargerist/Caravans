package primetoxinz.caravans.common.trades;

import net.minecraft.nbt.NBTTagCompound;
import primetoxinz.caravans.api.ITradeEntity;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.compat.EntityTradeable;
import primetoxinz.caravans.compat.IEntity;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class TradeEntity implements ITradeEntity {
    private EntityTradeable input;
    private EntityTradeable output;
    private int stock;

    public TradeEntity(IEntity input, IEntity output) {
        this.input = (EntityTradeable) input;
        this.output = (EntityTradeable) output;
    }

    public TradeEntity(NBTTagCompound tag) {
        input = EntityUtil.deserializeIEntity(tag.getCompoundTag("input"));
        output = EntityUtil.deserializeIEntity(tag.getCompoundTag("output"));
        stock = tag.getInteger("stock");
    }

    @Override
    public IEntity getInput() {
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
        return String.format("%s -> %s: %s", getInput(), ((EntityTradeable) getOutput()).getEntityClass(), getStock());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "entity");
        tag.setTag("input", EntityUtil.serializeIEntity(input));
        tag.setTag("output", EntityUtil.serializeIEntity(output));
        tag.setInteger("stock", stock);
        return tag;
    }


}
