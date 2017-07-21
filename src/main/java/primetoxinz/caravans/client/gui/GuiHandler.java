package primetoxinz.caravans.client.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import javax.annotation.Nullable;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Entity caravaneer = world.getEntityByID(ID);
        if (caravaneer instanceof EntityCaravaneer) {

            Caravan caravan = ((EntityCaravaneer) caravaneer).getCaravan();
            Merchant merchant = caravan.getMerchant(caravaneer.getUniqueID());
            ContainerMerchant container = new ContainerMerchant(caravan, merchant, player, (EntityCaravaneer) caravaneer);
            return container;
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Entity caravaneer = world.getEntityByID(ID);
        if (caravaneer instanceof EntityCaravaneer) {
            Caravan caravan = ((EntityCaravaneer) caravaneer).getCaravan();
            Merchant merchant = caravan.getMerchant(caravaneer.getUniqueID());
            return new GuiMerchant(new ContainerMerchant(caravan, merchant, player, (EntityCaravaneer) caravaneer));
        }
        return null;
    }
}
