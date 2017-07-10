package primetoxinz.caravans.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;

/**
 * Created by primetoxinz on 7/9/17.
 */
public interface IEntityTrade extends ITrade {

    Class<? extends EntityLiving> getOutput();
}
