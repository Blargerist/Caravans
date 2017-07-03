package primetoxinz.caravans.common.entity;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityUtil {
    public static Field isLeashed = ReflectionHelper.findField(EntityLiving.class, "isLeashed", "field_110169_bv");

    public static boolean isLeashed(EntityLiving living) {
        try {
            return (boolean) isLeashed.get(living);
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static void setLeashed(EntityLiving living, boolean value) {
        try {
            isLeashed.set(living, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Field bodyHelper = ReflectionHelper.findField(EntityLiving.class, "bodyHelper", "field_70762_j");

    public static void updateBody(EntityLiving living) {
        try {
            EntityBodyHelper helper = (EntityBodyHelper) bodyHelper.get(living);
            if (helper != null) {
                helper.updateRenderAngles();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
