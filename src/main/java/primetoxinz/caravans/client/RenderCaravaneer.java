package primetoxinz.caravans.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/1/17.
 */
public abstract class RenderCaravaneer<T extends EntityCaravaneer> extends RenderLiving<T> {

    public RenderCaravaneer(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    public abstract ResourceLocation getLeaderTexture(T leader);
    public abstract ResourceLocation getBaseTexture(T caravaneer);

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        if(entity.isLeader())
            return getLeaderTexture(entity);
        return getBaseTexture(entity);
    }
}
