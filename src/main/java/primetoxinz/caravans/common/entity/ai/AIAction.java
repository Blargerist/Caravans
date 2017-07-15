package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/2/17.
 */
public abstract class AIAction extends EntityAIBase {
    protected EntityCaravaneer entity;

    public AIAction(EntityCaravaneer entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }


    public abstract boolean isFinished();
}

