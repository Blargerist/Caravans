package primetoxinz.caravans.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import primetoxinz.caravans.api.IEntityTrade;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class ItemEntityTrade implements IEntityTrade {
    private ItemStack input;
    private Class<? extends EntityLiving> output;
    private int stock;

    public ItemEntityTrade(ItemStack input, Class<? extends EntityLiving> output) {
        this.input = input;
        this.output = output;
    }

    public ItemEntityTrade(NBTTagCompound tag) {
        try {
            input = new ItemStack(tag.getCompoundTag("input"));
            output = (Class<? extends EntityLiving>) getClass().forName(tag.getString("output"));
            stock = tag.getInteger("stock");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public Class<? extends EntityLiving> getOutput() {
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
        return String.format("%s -> %s : %s", getInput(), getOutput().getSimpleName(), getStock());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "item_entity");
        tag.setTag("input", input.serializeNBT());
        tag.setString("output", output.getCanonicalName());
        tag.setInteger("stock", stock);
        return tag;
    }


}
