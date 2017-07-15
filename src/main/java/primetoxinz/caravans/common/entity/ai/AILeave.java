package primetoxinz.caravans.common.entity.ai;

import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AILeave extends AIAction {

    public AILeave(EntityCaravaneer entity) {
        super(entity);
    }

    @Override
    public boolean shouldExecute() {
        entity.setDead();
        return entity.isEntityAlive();
    }



    @Override
    public boolean isFinished() {
        return entity.isDead;
    }
}
