package primetoxinz.caravans.common.entity;

import net.minecraft.world.World;
import primetoxinz.caravans.api.ICaravan;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class EntityCaravaneerZombie extends EntityCaravaneer {

    public EntityCaravaneerZombie(World world) {
        super(world);
    }

    @Override
    public ICaravan getCaravan() {
        return null;
    }
}
