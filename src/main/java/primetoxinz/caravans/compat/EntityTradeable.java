package primetoxinz.caravans.compat;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by primetoxinz on 7/14/17.
 */
public class EntityTradeable implements IEntity {

    private String classPath;
    private String nbt;

    public EntityTradeable() {
    }

    public Class<? extends EntityLiving> getEntityClass() {
        Class<? extends EntityLiving> clazz = null;
        try {
            clazz = (Class<? extends EntityLiving>) Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    @Override
    public String getEntityClassPath() {
        return classPath;
    }

    @Override
    public void setEntityClassPath(String classPath) {
        this.classPath = classPath;
    }

    public NBTTagCompound getCustomNBT() {
        if (this.nbt == null)
            return null;

        NBTTagCompound tag;
        try {
            tag = JsonToNBT.getTagFromJson(this.nbt);
        } catch (NBTException nbtexception) {
            throw new IllegalArgumentException("commands.blockdata.tagError" + new Object[]{nbtexception.getMessage()});
        }
        return tag;
    }

    @Override
    public void setCustomInfo(String info) {
        this.nbt = info;
    }

    @Override
    public String getCustomInfo() {
        return nbt;
    }

    @Override
    public boolean hasCustomInfo() {
        return getCustomInfo() != null;
    }

    @Override
    public String toString() {
        return String.format("%s %s\n", classPath,nbt);
    }
}
