package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityCreature;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AILeave extends AIAction {

    public AILeave(EntityCreature entity) {
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
