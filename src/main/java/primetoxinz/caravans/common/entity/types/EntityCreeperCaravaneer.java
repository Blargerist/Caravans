package primetoxinz.caravans.common.entity.types;

import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntityCreeperCaravaneer extends EntityCaravaneer {
    public EntityCreeperCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntityCreeperCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }
}
