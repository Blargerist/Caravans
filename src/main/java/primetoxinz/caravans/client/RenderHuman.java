package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.types.EntityHumanCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderHuman extends RenderCaravaneer<EntityHumanCaravaneer> {
    public RenderHuman(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPlayer(0, false), 0.5f);
    }

    @Override
    protected void preRenderCallback(EntityHumanCaravaneer entity, float partialTickTime) {
    }

    @Override
    public ResourceLocation getLeaderTexture(EntityHumanCaravaneer leader) {
        return leader.getLocationSkin();
    }

    @Override
    public ResourceLocation getBaseTexture(EntityHumanCaravaneer caravaneer) {
        return caravaneer.getLocationSkin();
    }

}
