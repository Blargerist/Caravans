package primetoxinz.caravans.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.client.models.ModelSkeleton;
import primetoxinz.caravans.common.entity.types.EntitySkeletonCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderSkeleton extends RenderCaravaneer<EntitySkeletonCaravaneer> {
    private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    public RenderSkeleton(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSkeleton(), 0.5f);
    }

    @Override
    protected void preRenderCallback(EntitySkeletonCaravaneer entity, float partialTickTime) {
        if(entity.isLeader()) {
            GlStateManager.scale(1.2F, 1.2F, 1.2F);
        }
    }

    @Override
    public ResourceLocation getLeaderTexture(EntitySkeletonCaravaneer leader) {
        return WITHER_SKELETON_TEXTURES;
    }

    @Override
    public ResourceLocation getBaseTexture(EntitySkeletonCaravaneer caravaneer) {
        return SKELETON_TEXTURES;
    }
}
