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
public class EntitySpiderCaravaneer extends EntityCaravaneer {
    public EntitySpiderCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntitySpiderCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    public SoundEvent getTradeSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }
}
