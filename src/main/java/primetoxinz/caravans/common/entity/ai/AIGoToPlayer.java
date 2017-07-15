package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class AIGoToPlayer extends AIAction {

    private EntityLivingBase target;
    private boolean finished;

    public AIGoToPlayer(EntityCaravaneer creature) {
        super(creature);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        this.target = this.entity.getAttackTarget();
        return this.target instanceof EntityPlayer;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void updateTask() {
        int range = (entity).isLeader() ? 5 : 10;
        if (this.target != null && entity.getDistanceToEntity(this.target) < range) {
            this.entity.setAttackTarget(null);
            this.entity.getNavigator().clearPathEntity();
            this.finished = true;
        }
        super.updateTask();
    }

    public boolean shouldContinueExecuting() {
        //TODO DISTANCE LIMITATIONS
        return !this.entity.getNavigator().noPath() && this.target.isEntityAlive();
    }

    public void resetTask() {
        this.target = null;
    }


    @Override
    public void startExecuting() {
        if (target != null)
            this.entity.getNavigator().tryMoveToEntityLiving(target, 1);
    }
}
