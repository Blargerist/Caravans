package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.EntityUtil;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AIWanderNear extends EntityAIBase {

    private BlockPos pos;
    private EntityCaravaneer entity;
    private int chance;

    public AIWanderNear(EntityCaravaneer caravaneer, int chance) {
        this.entity = caravaneer;
        this.chance = chance;
        setMutexBits(1);
    }

    @Nullable
    protected BlockPos getPosition() {
        return EntityUtil.generatePosition(entity.world, entity.getPosition(),5,5);
    }

    @Override
    public boolean shouldExecute() {
        if(entity.isLeader())
            return false;
        if (entity.isEntityAlive() && entity.getCaravan() != null && entity.getRNG().nextInt(chance) == 0) {
            BlockPos pos = getPosition();
            if (pos != null && pos.distanceSq(entity.getCaravan().getPosition()) < 16) {
                this.pos = pos;
                return true;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(pos.getX(),pos.getY(),pos.getZ(),0.5f);
    }
}
