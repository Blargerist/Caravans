package primetoxinz.caravans.client.gui.old.tab;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.Merchant;

import java.util.List;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class ContainerCaravan extends Container {
    private Caravan caravan;
    private EntityPlayer player;
    private IItemHandlerModifiable playerInventory;
    private World world;
    private List<TradeSlots> trades = Lists.newArrayList();

    public ContainerCaravan(IItemHandlerModifiable playerInventory, Caravan caravan, EntityPlayer player, World world) {
        this.caravan = caravan;
        this.playerInventory = playerInventory;
        this.player = player;
        this.world = world;
        setupPlayerInventory();
    }

    public void setupPlayerInventory() {
        int xOffset = 8;
        int yOffset = 66;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotItemHandler(playerInventory, j + i * 9 + 9, j * 18 + xOffset, 84 + i * 18 + yOffset));
            }
        }
        yOffset += 142;
        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new SlotItemHandler(playerInventory, k, k * 18 + xOffset, yOffset));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public List<Merchant> getMerchants() {
        return caravan.getMerchants();
    }
}
