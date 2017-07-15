package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import primetoxinz.caravans.common.trades.TradeItem;
import primetoxinz.caravans.common.trades.TradeItemEntity;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/14/17.
 */
public class Merchant implements INBTSerializable<NBTTagCompound> {

    protected ResourceLocation name;
    private List<ITrade> trades;
    private ItemStack icon = ItemStack.EMPTY;

    public Merchant(ResourceLocation name, List<ITrade> trades, ItemStack icon) {
        this.name = name;
        this.trades = trades;
        this.icon = icon;
    }

    public Merchant(NBTTagCompound tag) {
        this.deserializeNBT(tag);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("icon", icon.serializeNBT());
        tag.setString("registry", name.toString());

        NBTTagList trades = new NBTTagList();
        for (ITrade t : getTrades()) {
            trades.appendTag(t.serializeNBT());
        }
        tag.setTag("trades", trades);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        icon = new ItemStack((NBTTagCompound) nbt.getTag("icon"));
        this.name = new ResourceLocation(nbt.getString("registry"));
        List<ITrade> trades = Lists.newArrayList();
        NBTTagList list = nbt.getTagList("trades", 10);
        for (Iterator<NBTBase> iter = list.iterator(); iter.hasNext(); ) {
            NBTTagCompound tag = (NBTTagCompound) iter.next();
            trades.add(ITrade.deserializeNBT(tag));
        }
        this.trades = trades;
    }

    public List<ITrade> getTrades() {
        return trades;
    }

    public List<TradeItemEntity> getItemEntityTrades() {
        return getTrades().stream().filter(t -> t instanceof TradeItemEntity).map(t -> (TradeItemEntity) t).collect(Collectors.toList());
    }

    public List<ITradeEntity> getEntityTrades() {
        return getTrades().stream().filter(t -> t instanceof ITradeEntity).map(t -> (ITradeEntity) t).collect(Collectors.toList());
    }

    public List<TradeItem> getItemTrades() {
        return getTrades().stream().filter(t -> t instanceof TradeItem).map(t -> (TradeItem) t).collect(Collectors.toList());
    }

    public String getRealName() {
        return StringUtils.capitalize(name.getResourcePath());
    }


    @Override
    public String toString() {
        return name.toString();
    }

    @SideOnly(Side.CLIENT)
    public String getName() {
        return String.format("merchant.%s.%s", name.getResourceDomain(), name.getResourcePath());
    }

    public ItemStack getIcon() {
        return icon;
    }
}
