package primetoxinz.caravans.common.entity.types;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class EntitySkeletonCaravaneer extends EntityCaravaneer {
    public EntitySkeletonCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntitySkeletonCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    public SoundEvent getTradeSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }
}
