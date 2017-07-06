package primetoxinz.caravans.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.Merchant;

/**
 * Created by primetoxinz on 7/1/17.
 */
public interface ICaravaneer {

    ICaravaneer setCaravan(Caravan caravan);

    Caravan getCaravan();

    BlockPos getPosition();

    ICaravaneer spawn(World world, BlockPos pos);

    ICaravaneer setTarget(EntityPlayer player);

    int getID();


}
