package primetoxinz.caravans.common.entity.ai;

import com.google.common.collect.Lists;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.List;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AIState extends EntityAIBase {


    private List<AIAction> states;
    private AIAction action;

    public AIState(AIAction... states) {
        this.states = Lists.newArrayList(states);
        nextState();
    }


    public void nextState() {
        if (states.isEmpty())
            return;
        this.action = states.remove(0);
        this.setMutexBits(action.getMutexBits());
    }

    @Override
    public boolean shouldExecute() {
        if (action != null)
            return action.shouldExecute();
        return false;
    }

    @Override
    public void startExecuting() {
        if (action != null)
            action.startExecuting();
    }

    @Override
    public void updateTask() {
        if (action != null) {
            action.updateTask();
            if (action.isFinished())
                nextState();
        }

    }

    @Override
    public boolean shouldContinueExecuting() {
        if (action != null)
            return action.shouldContinueExecuting();
        return false;
    }


}
