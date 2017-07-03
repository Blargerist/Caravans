package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.entity.EntityCaravanerZombie;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public class RenderZombie extends RenderLiving<EntityCaravanerZombie> {
    public RenderZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityCaravanerZombie entity) {
        return new ResourceLocation("minecraft:textures/entity/zombie/zombie.png");
    }
}
