package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import primetoxinz.caravans.common.ItemEntityTrade;
import primetoxinz.caravans.common.ItemTrade;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static primetoxinz.caravans.api.CaravanAPI.processName;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class MerchantBuilder extends IForgeRegistryEntry.Impl<MerchantBuilder> {

    private List<ITrade> trades;
    private ItemStack icon = ItemStack.EMPTY;

    public MerchantBuilder(ResourceLocation registerName, ItemStack icon) {
        setRegistryName(registerName);
        this.trades = Lists.newArrayList();
        this.icon = icon;
    }

    public MerchantBuilder(String name, ItemStack icon) {
        this(new ResourceLocation(processName(name)), icon);
    }

    public MerchantBuilder(ResourceLocation loc, List<ITrade> trades, ItemStack icon) {
        setRegistryName(loc);
        this.trades = trades;
        this.icon = icon;
    }

    public void addTrade(ITrade trade) {
        this.trades.add(trade);
    }


    public Merchant create() {
        return new Merchant(getRegistryName(), trades.stream().map(ITrade::create).collect(Collectors.toList()), icon);
    }
}

