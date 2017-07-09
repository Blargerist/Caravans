package primetoxinz.caravans.common.entity.types;

import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntitySkeletonCaravaneer extends EntityCaravaneer {
    public EntitySkeletonCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntitySkeletonCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }
}
