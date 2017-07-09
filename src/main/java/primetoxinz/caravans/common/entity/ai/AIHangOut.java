package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityCreature;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class AIHangOut extends AIAction {
    private int MAX_HANGOUT_TICKS = 20*30;
    private int ticks;

    public AIHangOut(EntityCreature entity) {
        super(entity);
    }

    @Override
    public boolean shouldExecute() {
        return !isFinished();
    }

    @Override
    public void updateTask() {
        super.updateTask();
        ticks++;
        System.out.println(ticks);
    }

    @Override
    public boolean isFinished() {
        return ticks >= MAX_HANGOUT_TICKS;
    }
}
