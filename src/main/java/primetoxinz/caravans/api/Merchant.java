package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class Merchant extends IForgeRegistryEntry.Impl<Merchant> {

    private List<ITrade> trades = Lists.newArrayList();
    private ItemStack icon = ItemStack.EMPTY;

    public Merchant(ResourceLocation name, ITrade... trades) {
        this.trades = Lists.newArrayList(trades);
        setRegistryName(name);
    }

    public Merchant(String name, ITrade... trades) {
        this(new ResourceLocation(name), trades);
    }

    public void addTrade(ITrade trade) {
        trades.add(trade);
    }

    public List<ITrade> getTrades() {
        return trades;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Merchant setIcon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    @SideOnly(Side.CLIENT)
    public String getLocalizedName() {
        return I18n.format(String.format("merchant.%s.%s", getRegistryName().getResourceDomain(), getRegistryName().getResourcePath()));
    }

    @Override
    public String toString() {
        return getRegistryName().toString();
    }
}
