package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.ai.EntityAIBase;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class AIWanderNear extends EntityAIBase {

    private EntityCaravaneer caravaneer;
    public AIWanderNear(EntityCaravaneer caravaneer) {
        this.caravaneer = caravaneer;
    }


    @Override
    public boolean shouldExecute() {
        return false;
    }
}
