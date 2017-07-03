package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AILook extends EntityAIBase {
    private EntityLiving entity;

    private int lookTime;
    private final float chance;


    public AILook(EntityLiving entity) {
        this.entity = entity;
        this.setMutexBits(2);
        this.chance = 0.02F;
    }


    @Override
    public boolean shouldExecute() {
        if (this.entity.getRNG().nextFloat() >= this.chance)
            return false;
        return entity.getAttackTarget() != null && entity.getAttackTarget().isEntityAlive();
    }

    public void startExecuting() {
        this.lookTime = 40 + this.entity.getRNG().nextInt(40);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return entity.getAttackTarget() != null && lookTime > 0;
    }

    @Override
    public void updateTask() {
        if(entity.getAttackTarget() != null) {
            this.entity.getLookHelper().setLookPositionWithEntity(entity.getAttackTarget(), (float) this.entity.getHorizontalFaceSpeed(), (float) this.entity.getVerticalFaceSpeed());
        }
        this.lookTime--;
    }
}
