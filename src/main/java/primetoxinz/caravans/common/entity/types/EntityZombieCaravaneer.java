package primetoxinz.caravans.common.entity.types;

import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntityZombieCaravaneer extends EntityCaravaneer {
    public EntityZombieCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntityZombieCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }
}
