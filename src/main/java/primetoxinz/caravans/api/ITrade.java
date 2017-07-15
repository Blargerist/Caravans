package primetoxinz.caravans.api;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import primetoxinz.caravans.common.trades.TradeEntity;
import primetoxinz.caravans.common.trades.TradeItem;
import primetoxinz.caravans.common.trades.TradeItemEntity;

/**
 * Created by primetoxinz on 7/4/17.
 */
public interface ITrade {

    Object getInput();

    Object getOutput();

    void setStock(int stock);

    int getStock();

    NBTTagCompound serializeNBT();

    static ITrade deserializeNBT(NBTTagCompound tag) {
        switch (tag.getString("type")) {
            case "item":
                return new TradeItem(tag);
            case "item_entity":
                return new TradeItemEntity(tag);
            case "entity":
                return new TradeEntity(tag);
            default:
                return null;
        }
    }

    default void onTrade(World world, EntityLiving living) {
        if(getStock() > 0)
            setStock(Math.max(0, getStock() - 1));
    }

    default boolean isInStock() {
        if(getStock() < 0)
            return true;
        return getStock() > 0;
    }

    default ITrade create() {
        setStock(1);
        return this;
    }
}