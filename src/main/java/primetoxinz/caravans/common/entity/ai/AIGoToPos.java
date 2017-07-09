package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.BlockPos;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AIGoToPos extends AIAction {

    private static final int STOP_RANGE = 10;
    private BlockPos pos;
    private boolean finished;
    public AIGoToPos(EntityCaravaneer entity) {
        super(entity);

        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        this.pos = ((EntityCaravaneer)entity).getOrigins();
        return pos != null;
    }


    @Override
    public void updateTask() {
        if (this.pos != null && entity.getDistance(pos.getX(), pos.getY(), pos.getZ()) < STOP_RANGE) {
            this.entity.getNavigator().clearPathEntity();
            this.finished = true;
        }
        super.updateTask();
    }

    @Override
    public void startExecuting() {
        entity.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), 1);
    }

    public boolean shouldContinueExecuting() {
        return !entity.getNavigator().noPath();
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
