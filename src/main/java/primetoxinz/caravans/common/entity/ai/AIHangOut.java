package primetoxinz.caravans.common.entity.ai;

import primetoxinz.caravans.ConfigHandler;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AIHangOut extends AIAction {
    public AIHangOut(EntityCaravaneer entity) {
        super(entity);
    }

    @Override
    public boolean shouldExecute() {
        return !isFinished();
    }

    @Override
    public void updateTask() {
        super.updateTask();
        System.out.println(((EntityCaravaneer) entity).stay++);

    }

    @Override
    public boolean isFinished() {
        return ((EntityCaravaneer) entity).stay >= ConfigHandler.hangoutTicks;
    }
}
