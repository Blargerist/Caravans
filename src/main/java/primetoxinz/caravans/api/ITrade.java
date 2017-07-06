package primetoxinz.caravans.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by primetoxinz on 7/4/17.
 */
public interface ITrade {

    ItemStack getInput();

    ItemStack getOutput();

}
