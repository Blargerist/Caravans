package primetoxinz.caravans.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import primetoxinz.caravans.api.ICaravan;

/**
 * Created by primetoxinz on 7/1/17.
 */
public interface ICaravaner {

    ICaravaner setCaravan(ICaravan caravan);

    ICaravan getCaravan();

    BlockPos getPosition();

    ICaravaner spawn(World world, BlockPos pos, ICaravan caravan);

    ICaravaner setTarget(EntityPlayer player);

    int getID();


}
