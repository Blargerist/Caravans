package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.EntityCaravaneerTrader;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class RenderTrader extends RenderLiving<EntityCaravaneerTrader> {
    public RenderTrader(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelVillager(0.0F), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCaravaneerTrader entity) {
        return new ResourceLocation("minecraft:textures/entity/villager/villager.png");
    }
}
