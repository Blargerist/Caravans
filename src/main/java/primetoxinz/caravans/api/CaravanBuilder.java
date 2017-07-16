package primetoxinz.caravans.api;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by primetoxinz on 7/4/17.
 */
public class CaravanBuilder extends IForgeRegistryEntry.Impl<CaravanBuilder> {

    protected Class<? extends EntityCaravaneer> leaderClass;
    protected Map<MerchantBuilder, Class<? extends EntityCaravaneer>> followerClasses;
    protected String stage;

    public CaravanBuilder(ResourceLocation name, Class<? extends EntityCaravaneer> leaderClass, Object... objects) {
        this.leaderClass = leaderClass;
        this.followerClasses = new MerchantMap(objects);
        setRegistryName(name);
    }

    public CaravanBuilder(String name, Class<? extends EntityCaravaneer> leaderClass, Object... objects) {
        this(new ResourceLocation(name), leaderClass, objects);
    }

    public CaravanBuilder addFollower(Class<? extends EntityCaravaneer> follower, MerchantBuilder merchant) {
        this.followerClasses.put(merchant, follower);
        return this;
    }

    public Caravan create(World world) {
        Caravan caravan = new Caravan(this.getRegistryName(), world, leaderClass, followerClasses);
        caravan.setGameStage(getStage());
        return caravan;
    }

    public class MerchantMap extends HashMap<MerchantBuilder, Class<? extends EntityCaravaneer>> {

        public MerchantMap(Object... objects) {
            for (int i = 0; i < objects.length / 2; i++)
                put((MerchantBuilder) objects[i * 2], (Class<? extends EntityCaravaneer>) objects[i * 2 + 1]);
        }

        @Override
        public Class<? extends EntityCaravaneer> put(MerchantBuilder merchant, Class<? extends EntityCaravaneer> aClass) {
            return super.put(merchant, aClass);
        }
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    @Override
    public String toString() {
        return String.format("CaravanBuilder:%s -> %s. Stage:%s", leaderClass,followerClasses, stage);
    }
}
