package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.types.EntityVillagerCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderVillager extends RenderCaravaneer<EntityVillagerCaravaneer> {
    public RenderVillager(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelVillager(0.0F), 0.5f);
    }

    @Override
    public ResourceLocation getLeaderTexture(EntityVillagerCaravaneer leader) {
        return new ResourceLocation("minecraft:textures/entity/villager/priest.png");
    }

    @Override
    public ResourceLocation getBaseTexture(EntityVillagerCaravaneer caravaneer) {
        return new ResourceLocation("minecraft:textures/entity/villager/villager.png");
    }
}
