package primetoxinz.caravans.entity;

import net.minecraft.world.World;
import primetoxinz.caravans.capability.ICaravan;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityCaravanerTrader extends EntityCaravaner {

    public EntityCaravanerTrader(World world) {
        super(world);
    }
    @Override
    public ICaravan getCaravan() {
        return null;
    }


}
