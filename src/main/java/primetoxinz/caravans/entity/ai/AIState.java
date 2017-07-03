package primetoxinz.caravans.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by primetoxinz on 7/2/17.
 */
public abstract class AIState extends EntityAIBase {
    protected EntityLiving entity;

    public AIState(EntityLiving entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }


    public abstract boolean isFinished();
}

