package primetoxinz.caravans.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import primetoxinz.caravans.capability.ICaravaneer;

import java.util.List;

/**
 * Created by primetoxinz on 7/1/17.
 */
@SuppressWarnings("unused")
public interface ICaravan extends IForgeRegistryEntry<ICaravan> {

    BlockPos getPosition(World world);

    CaravanStatus getStatus();

    ICaravan setStatus(CaravanStatus status);

    List<ICaravaneer> getFollowers(World world);

    ICaravan addFollower(Class caravaner);

    ICaravan generateFollowers(int amount);

    ICaravan setLeader(Class caravaner);

    ICaravaneer getLeader(World world);

    void spawn(World world, BlockPos pos, EntityPlayer player);

    void open(EntityPlayer player, EnumHand hand, Entity entity);

    enum CaravanStatus {
        ARRIVING,
        SELLING,
        LEAVING,
        LEAVE;
        public static CaravanStatus[] VALUES = values();
    }


}
