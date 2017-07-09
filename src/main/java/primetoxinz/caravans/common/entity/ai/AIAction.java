package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by primetoxinz on 7/2/17.
 */
public abstract class AIAction extends EntityAIBase {
    protected EntityCreature entity;

    public AIAction(EntityCreature entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }


    public abstract boolean isFinished();
}

