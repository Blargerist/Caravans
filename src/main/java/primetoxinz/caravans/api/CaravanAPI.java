package primetoxinz.caravans.api;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import primetoxinz.caravans.common.Trade;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class CaravanAPI {

    public final static IForgeRegistry<CaravanBuilder> CARAVANS = GameRegistry.findRegistry(CaravanBuilder.class);

    public final static IForgeRegistry<Merchant> MERCHANTS = GameRegistry.findRegistry(Merchant.class);

    public static void init() {

        GameRegistry.register(new Merchant("caravans:test7",
                new Trade(new ItemStack(Items.BREAD), new ItemStack(Items.DIAMOND)),
                new Trade(new ItemStack(Items.FLINT_AND_STEEL), new ItemStack(Items.EMERALD)),
                new Trade(new ItemStack(Items.RABBIT_STEW), new ItemStack(Items.DIAMOND)),
                new Trade(new ItemStack(Items.TNT_MINECART), new ItemStack(Items.SHIELD)),
                new Trade(new ItemStack(Items.BREAD), new ItemStack(Items.DIAMOND)),
                new Trade(new ItemStack(Items.BREAD), new ItemStack(Items.DIAMOND)),
                new Trade(new ItemStack(Items.BREAD), new ItemStack(Items.DIAMOND))
        ).setIcon(new ItemStack(Items.DIAMOND_PICKAXE)));

        GameRegistry.register(new Merchant("caravans:test8",
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT)),
                new Trade(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.WHEAT))
        ).setIcon(new ItemStack(Items.DIAMOND_HOE)));

        GameRegistry.register(new Merchant("caravans:test").setIcon(new ItemStack(Items.TNT_MINECART)));
        GameRegistry.register(new Merchant("caravans:test2").setIcon(new ItemStack(Items.FLINT)));
        GameRegistry.register(new Merchant("caravans:test3").setIcon(new ItemStack(Items.RABBIT)));
        GameRegistry.register(new Merchant("caravans:test4").setIcon(new ItemStack(Items.SHIELD)));
        GameRegistry.register(new Merchant("caravans:test5").setIcon(new ItemStack(Items.EMERALD)));
        GameRegistry.register(new Merchant("caravans:test6").setIcon(new ItemStack(Items.BREAD)));

        GameRegistry.register(new CaravanBuilder("caravans:basic", EntityCaravaneer.class,
                getMerchant("caravans:test"), EntityCaravaneer.class,
                getMerchant("caravans:test2"), EntityCaravaneer.class,
                getMerchant("caravans:test3"), EntityCaravaneer.class,
                getMerchant("caravans:test4"), EntityCaravaneer.class,
                getMerchant("caravans:test5"), EntityCaravaneer.class,
                getMerchant("caravans:test6"), EntityCaravaneer.class,
                getMerchant("caravans:test7"), EntityCaravaneer.class,
                getMerchant("caravans:test8"), EntityCaravaneer.class

        ));
    }

    public static CaravanBuilder getCaravan(ResourceLocation loc) {
        return CARAVANS.getValue(loc);
    }

    public static CaravanBuilder getCaravan(String loc) {
        System.out.println("caravan?"+loc);
        if (!loc.contains(":"))
            loc = "caravans:" + loc;
        return getCaravan(new ResourceLocation(loc));
    }

    public static Merchant getMerchant(ResourceLocation loc) {
        return MERCHANTS.getValue(loc);
    }

    public static Merchant getMerchant(String loc) {
        if(loc == null)
            return null;
        if (!loc.contains(":"))
            loc = "caravans:" + loc;
        System.out.println("Location " + loc);
        return getMerchant(new ResourceLocation(loc));
    }
}
