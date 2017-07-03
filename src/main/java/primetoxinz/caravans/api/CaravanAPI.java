package primetoxinz.caravans.api;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import primetoxinz.caravans.capability.ICaravan;
import primetoxinz.caravans.entity.EntityCaravanerDonkey;
import primetoxinz.caravans.entity.EntityCaravanerTrader;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class CaravanAPI {


    public final static IForgeRegistry<ICaravan> CARAVANS = GameRegistry.findRegistry(ICaravan.class);

    @GameRegistry.ObjectHolder("caravans:basic")
    public final static ICaravan basic = null;

    public static void init() {
        GameRegistry.register(new ICaravan.Caravan("basic", EntityCaravanerTrader.class, Lists.newArrayList(EntityCaravanerTrader.class, EntityCaravanerTrader.class, EntityCaravanerTrader.class, EntityCaravanerTrader.class, EntityCaravanerDonkey.class)));
    }

}
