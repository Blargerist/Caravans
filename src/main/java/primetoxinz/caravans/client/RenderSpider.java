package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.EntityCaravaneer;
import primetoxinz.caravans.common.entity.types.EntitySpiderCaravaneer;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class RenderSpider extends RenderCaravaneer<EntitySpiderCaravaneer> {
    private static final ResourceLocation SPIDER_TEXTURES = new ResourceLocation("textures/entity/spider/spider.png");
    private static final ResourceLocation CAVE_SPIDER_TEXTURES = new ResourceLocation("textures/entity/spider/cave_spider.png");

    public RenderSpider(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSpider(),1.0f);
    }

    @Override
    public ResourceLocation getLeaderTexture(EntityCaravaneer leader) {
        return CAVE_SPIDER_TEXTURES;
    }

    @Override
    public ResourceLocation getBaseTexture(EntityCaravaneer caravaneer) {
        return SPIDER_TEXTURES;
    }

}
