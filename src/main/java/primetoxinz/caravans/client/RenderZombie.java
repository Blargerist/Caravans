package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.types.EntityZombieCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderZombie extends RenderCaravaneer<EntityZombieCaravaneer> {
    private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");

    public RenderZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5f);
    }

    @Override
    public ResourceLocation getLeaderTexture(EntityZombieCaravaneer leader) {
        return ZOMBIE_TEXTURES;
    }

    @Override
    public ResourceLocation getBaseTexture(EntityZombieCaravaneer caravaneer) {
        return ZOMBIE_TEXTURES;
    }
}
