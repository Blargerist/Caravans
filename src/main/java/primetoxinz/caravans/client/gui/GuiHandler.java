package primetoxinz.caravans.client.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.capability.ICaravaneer;

import javax.annotation.Nullable;

import static net.minecraft.util.EnumFacing.UP;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(ID);
        if (entity instanceof ICaravaneer) {
            ICaravaneer caravaner = (ICaravaneer) entity;
            Caravan caravan = caravaner.getCaravan();
            if (caravan != null)
                return new ContainerCaravan((IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, UP), caravan, world);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Entity entity = world.getEntityByID(ID);
        if (entity instanceof ICaravaneer) {
            ICaravaneer caravaner = (ICaravaneer) entity;
            Caravan caravan = caravaner.getCaravan();
            if (caravan != null)
                return new GuiCaravan((IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, UP), caravan, world);
        }
        return null;
    }
}
