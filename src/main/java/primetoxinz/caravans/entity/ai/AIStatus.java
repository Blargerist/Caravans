package primetoxinz.caravans.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;

import java.util.List;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AIStatus extends EntityAIBase {


    private List<AIState> states;
    private AIState currentState;

    public AIStatus(List<AIState> states) {
        this.states = states;
        nextState();
    }


    public void nextState() {
        if (states.isEmpty())
            return;
        this.currentState = states.remove(0);
        this.setMutexBits(currentState.getMutexBits());
    }

    @Override
    public boolean shouldExecute() {
        if (currentState != null)
            return currentState.shouldExecute();
        return false;
    }

    @Override
    public void startExecuting() {
        if (currentState != null)
            currentState.startExecuting();
    }

    @Override
    public void updateTask() {
        if (currentState != null) {
            currentState.updateTask();
            if (currentState.isFinished())
                nextState();
        }

    }

    @Override
    public boolean shouldContinueExecuting() {
        if (currentState != null)
            return currentState.shouldContinueExecuting();
        return false;
    }


}
