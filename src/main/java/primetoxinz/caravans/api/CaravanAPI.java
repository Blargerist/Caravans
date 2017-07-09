package primetoxinz.caravans.api;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import primetoxinz.caravans.ConfigHandler;

import java.util.List;
import java.util.Random;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class CaravanAPI {

    public final static IForgeRegistry<CaravanBuilder> CARAVANS = GameRegistry.findRegistry(CaravanBuilder.class);

    public final static IForgeRegistry<Merchant> MERCHANTS = GameRegistry.findRegistry(Merchant.class);

    public static CaravanBuilder getCaravan(ResourceLocation loc) {
        return CARAVANS.getValue(loc);
    }

    public static CaravanBuilder getCaravan(String loc) {
        return getCaravan(new ResourceLocation(loc));
    }

    public static Merchant getMerchant(ResourceLocation loc) {
        return MERCHANTS.getValue(loc);
    }

    public static Merchant getMerchant(String loc) {
        if (loc == null)
            return null;
        return getMerchant(new ResourceLocation(loc));
    }

    public static String processName(String name) {
        return name;
    }

    public static CaravanBuilder getRandomCaravan(World world) {
        List<CaravanBuilder> builders = CARAVANS.getValues();
        int i = world.rand.nextInt(builders.size());
        return builders.get(i);
    }
}
