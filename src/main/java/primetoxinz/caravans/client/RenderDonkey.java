package primetoxinz.caravans.client;

import net.minecraft.client.renderer.entity.RenderAbstractHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.util.ResourceLocation;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class RenderDonkey extends RenderAbstractHorse {

    public RenderDonkey(RenderManager manager) {
        super(manager,0.87F);
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractHorse entity) {
        return new ResourceLocation("textures/entity/horse/donkey.png");
    }
}
