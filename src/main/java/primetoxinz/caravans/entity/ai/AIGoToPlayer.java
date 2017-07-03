package primetoxinz.caravans.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class AIGoToPlayer extends AIState {

    private static final int STOP_RANGE = 10;
    private EntityLivingBase target;
    private boolean finished;

    public AIGoToPlayer(EntityLiving creature) {
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
        if (this.target != null && entity.getDistanceToEntity(this.target) < STOP_RANGE) {
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
