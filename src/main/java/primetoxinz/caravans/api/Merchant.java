package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.common.ItemEntityTrade;
import primetoxinz.caravans.common.ItemTrade;

import java.util.List;
import java.util.stream.Collectors;

import static primetoxinz.caravans.api.CaravanAPI.processName;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class Merchant extends IForgeRegistryEntry.Impl<Merchant> {

    private List<ITrade> trades = Lists.newArrayList();
    private ItemStack icon = ItemStack.EMPTY;

    public Merchant(ResourceLocation registerName) {
        setRegistryName(registerName);
    }

    public Merchant(String name) {
        this(new ResourceLocation(processName(name)));
    }

    public void addTrade(ITrade trade) {
        this.trades.add(trade);
    }

    public void removeTrade(ITrade trade) {
        this.trades.remove(trade);
    }

    public List<ITrade> getTrades() {
        return trades;
    }

    public List<ItemEntityTrade> getItemEntityTrades() {
        return getTrades().stream().filter(t -> t instanceof ItemEntityTrade).map(t -> (ItemEntityTrade) t).collect(Collectors.toList());
    }

    public List<IEntityTrade> getEntityTrades() {
        return getTrades().stream().filter(t -> t instanceof IEntityTrade).map(t -> (IEntityTrade) t).collect(Collectors.toList());
    }

    public List<ItemTrade> getItemTrades() {
        return getTrades().stream().filter(t -> t instanceof ItemTrade).map(t -> (ItemTrade) t).collect(Collectors.toList());
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Merchant setIcon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public String getName() {
        return String.format("merchant.%s.%s", getRegistryName().getResourceDomain(), getRegistryName().getResourcePath());
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }


    public String getRealName() {
        return StringUtils.capitalize(getRegistryName().getResourcePath());
    }

}
