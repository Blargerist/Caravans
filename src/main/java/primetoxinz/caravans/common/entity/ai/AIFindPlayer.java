package primetoxinz.caravans.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class AIFindPlayer extends EntityAITarget{

    public AIFindPlayer(EntityCreature creature) {
        super(creature, false);
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        target = getNearestPlayer(taskOwner.world,taskOwner.getPosition(),1000000,1000000);
        return target != null;
    }

    @Nullable
    public static EntityPlayer getNearestPlayer(World world, BlockPos pos, int maxX, int maxZ) {
        return world.getNearestAttackablePlayer(pos,maxX,maxZ);
    }
}
