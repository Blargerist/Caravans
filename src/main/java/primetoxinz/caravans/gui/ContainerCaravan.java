package primetoxinz.caravans.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import primetoxinz.caravans.api.ICaravan;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class ContainerCaravan extends Container {
    private ICaravan caravan;
    private IItemHandlerModifiable inventory;
    private World world;

    public ContainerCaravan(IItemHandlerModifiable inventory, ICaravan caravan, World world) {
        this.caravan = caravan;
        this.inventory = inventory;
        this.world = world;
//        addSlotToContainer(new SlotItemHandler(inventory,)vvvvvvvvvvvvvvvvvvvvvvvv

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new SlotItemHandler(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new SlotItemHandler(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }
}
