package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AIState extends EntityAIBase {

    private EntityCaravaneer caravaneer;
    private AIAction action;

    public AIState(EntityCaravaneer caravaneer) {
        this.caravaneer = caravaneer;
    }

    public void setAction(Caravan.Status status) {
        switch (status) {
            case ARRIVING:
                this.action = new AIGoToPlayer(caravaneer);
                break;
            case TRADING:
                this.action = new AIHangOut(caravaneer);
                break;
            case LEAVING:
                this.action = new AIGoToPos(caravaneer);
                break;
            case GONE:
                this.action = new AILeave(caravaneer);
                break;
        }
    }

    @Override
    public boolean shouldExecute() {
        if (hasAction())
            return action.shouldExecute();
        return false;
    }

    @Override
    public void startExecuting() {
        if (hasAction())
            action.startExecuting();
    }

    @Override
    public void updateTask() {
        if (hasAction()) {
            action.updateTask();
            if (action.isFinished()) {
                if(caravaneer.isLeader())
                    caravaneer.getCaravan().onFinish(caravaneer);
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (hasAction())
            return action.shouldContinueExecuting();
        return false;
    }

    public boolean hasAction() {
        return action != null;
    }
}
