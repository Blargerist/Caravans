package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.types.EntityCreeperCaravaneer;
import primetoxinz.caravans.common.entity.types.EntitySkeletonCaravaneer;
import primetoxinz.caravans.common.entity.types.EntitySpiderCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderCreeper extends RenderCaravaneer<EntityCreeperCaravaneer> {
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");

    public RenderCreeper(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCreeper(), 0.5f);
    }

    @Override
    protected void preRenderCallback(EntityCreeperCaravaneer entity, float partialTickTime) {
        if (entity.isLeader()) {
            GlStateManager.scale(1.5f,1.5f,1.5f);
        }
    }

    @Override
    public ResourceLocation getLeaderTexture(EntityCreeperCaravaneer leader) {
        return CREEPER_TEXTURES;
    }

    @Override
    public ResourceLocation getBaseTexture(EntityCreeperCaravaneer caravaneer) {
        return CREEPER_TEXTURES;
    }

}
