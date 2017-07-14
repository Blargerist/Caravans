package primetoxinz.caravans.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import primetoxinz.caravans.api.IEntityTrade;
import primetoxinz.caravans.api.ITrade;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntityTrade implements IEntityTrade {
    private Class<? extends EntityLiving> input;
    private Class<? extends EntityLiving> output;
    private int stock;

    public EntityTrade(Class<? extends EntityLiving> input, Class<? extends EntityLiving> output) {
        this.input = input;
        this.output = output;
    }

    public EntityTrade(NBTTagCompound tag) {
        try {
            input = (Class<? extends EntityLiving>) getClass().forName(tag.getString("input"));
            output = (Class<? extends EntityLiving>) getClass().forName(tag.getString("output"));
            stock = tag.getInteger("stock");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<? extends EntityLiving> getInput() {
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
        return String.format("%s -> %s: %s", getInput(), getOutput().getSimpleName(), getStock());
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "entity");
        tag.setString("input", input.getCanonicalName());
        tag.setString("output", output.getCanonicalName());
        tag.setInteger("stock", stock);
        return tag;
    }


}
