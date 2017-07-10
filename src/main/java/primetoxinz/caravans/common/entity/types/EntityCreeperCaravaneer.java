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
public class EntityCreeperCaravaneer extends EntityCaravaneer {
    public EntityCreeperCaravaneer(World worldIn) {
        super(worldIn);
    }

    public EntityCreeperCaravaneer(World worldIn, Caravan caravan) {
        super(worldIn, caravan);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    public SoundEvent getTradeSound() {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }
}
