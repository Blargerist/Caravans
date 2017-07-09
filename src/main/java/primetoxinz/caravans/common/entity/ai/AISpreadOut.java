package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AISpreadOut extends EntityAIBase {
    private EntityLiving entity;

    public AISpreadOut(EntityLiving entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        if (entity == null)
            return false;
        World world = entity.world;
        List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, entity.getEntityBoundingBox().expandXyz(0.5f));
        return !list.isEmpty();
    }

    @Override
    public void startExecuting() {
        Random rand = entity.world.rand;
        double x = rand.nextDouble() * 2;
        double z = rand.nextDouble() * 2;
        entity.move(MoverType.SELF, x, 0, z);
    }
}
