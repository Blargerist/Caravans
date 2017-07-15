package primetoxinz.caravans.compat;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Created by primetoxinz on 7/14/17.
 */
@ZenClass("mods.caravans.Entity")
public interface IEntity {

    @ZenMethod
    String getEntityClassPath();

    @ZenMethod
    void setEntityClassPath(String clazz);

    @ZenMethod
    void setCustomInfo(String tag);

    @ZenMethod
    String getCustomInfo();

    @ZenMethod
    boolean hasCustomInfo();
}
