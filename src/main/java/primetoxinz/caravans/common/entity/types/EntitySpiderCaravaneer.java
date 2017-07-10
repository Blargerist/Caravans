package primetoxinz.caravans.common.entity.types;

import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntitySpiderCaravaneer extends EntityCaravaneer {
    public EntitySpiderCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntitySpiderCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }
}
