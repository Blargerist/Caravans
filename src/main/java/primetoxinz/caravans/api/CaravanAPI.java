package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import primetoxinz.caravans.common.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneerDonkey;
import primetoxinz.caravans.common.entity.EntityCaravaneerTrader;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class CaravanAPI {

    public final static IForgeRegistry<ICaravan> CARAVANS = GameRegistry.findRegistry(ICaravan.class);

    @GameRegistry.ObjectHolder("caravans:basic")
    public final static ICaravan basic = null;

    @GameRegistry.ObjectHolder("caravans:donkey")
    public final static ICaravan donkey = null;

    public static void init() {
        GameRegistry.register(new Caravan("basic", EntityCaravaneerTrader.class, Lists.newArrayList(EntityCaravaneerTrader.class, EntityCaravaneerTrader.class, EntityCaravaneerTrader.class, EntityCaravaneerTrader.class)));
        GameRegistry.register(new Caravan("donkey", EntityCaravaneerTrader.class, Lists.newArrayList(EntityCaravaneerTrader.class, EntityCaravaneerTrader.class, EntityCaravaneerTrader.class, EntityCaravaneerTrader.class, EntityCaravaneerDonkey.class)));
    }

    public static ICaravan getCaravan(ResourceLocation loc) {
        return CARAVANS.getValue(loc);
    }

    public static ICaravan getCaravan(String loc) {
        return getCaravan(new ResourceLocation(loc));
    }
}
