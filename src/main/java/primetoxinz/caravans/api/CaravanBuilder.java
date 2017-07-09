package primetoxinz.caravans.api;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.util.HashMap;
import java.util.Map;

import static primetoxinz.caravans.api.CaravanAPI.processName;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class CaravanBuilder extends IForgeRegistryEntry.Impl<CaravanBuilder> {

    protected Class<? extends EntityCaravaneer> leaderClass;
    protected Map<Merchant, Class<? extends EntityCaravaneer>> followerClasses;

    public CaravanBuilder(ResourceLocation name, Class<? extends EntityCaravaneer> leaderClass, Object... objects) {
        this.leaderClass = leaderClass;
        this.followerClasses = new MerchantMap(objects);
        setRegistryName(name);
    }

    public CaravanBuilder(String name, Class<? extends EntityCaravaneer> leaderClass, Object... objects) {
        this(new ResourceLocation(processName(name)), leaderClass, objects);
    }

    public CaravanBuilder addFollower(Class<? extends EntityCaravaneer> follower, Merchant merchant) {
        this.followerClasses.put(merchant, follower);
        return this;
    }

    public Caravan create(World world) {
        return new Caravan(this.getRegistryName(), world, leaderClass, followerClasses);
    }

    public class MerchantMap extends HashMap<Merchant, Class<? extends EntityCaravaneer>> {

        public MerchantMap(Object... objects) {
            for (int i = 0; i < objects.length / 2; i++)
                put((Merchant) objects[i * 2], (Class<? extends EntityCaravaneer>) objects[i * 2 + 1]);
        }

        @Override
        public Class<? extends EntityCaravaneer> put(Merchant merchant, Class<? extends EntityCaravaneer> aClass) {
            return super.put(merchant, aClass);
        }
    }


}
