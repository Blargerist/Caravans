package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.client.gui.slot.SlotOutput;
import primetoxinz.caravans.common.ItemUtils;

import java.util.List;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class ContainerMerchant extends Container {

    protected Caravan caravan;
    protected EntityPlayer player;
    protected InventoryMerchant inventoryMerchant;
    protected Merchant merchant;
    protected IItemHandlerModifiable playerInventory;

    protected World world;

    protected List<SlotInput> inputs = Lists.newArrayList();
    protected List<SlotOutput> outputs = Lists.newArrayList();


    public ContainerMerchant(Caravan caravan, Merchant merchant, EntityPlayer player) {
        this.caravan = caravan;
        this.world = player.world;
        this.merchant = merchant;
        this.playerInventory = (IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.player = player;
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

        if (merchant != null) {
            this.inventoryMerchant = new InventoryMerchant(merchant);
            int size = getSize();
            for (int i = 0; i < size; i++) {
                addInput(new SlotInput(inventoryMerchant, i, 0, 0));
                addOutput(new SlotOutput(inventoryMerchant, i, 36, 0));
            }
        }

    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return merchant != null;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId < 36) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        } else {
            Slot slot = inventorySlots.get(slotId);
            if (slot instanceof SlotOutput) {
                int i = outputs.indexOf(slot);
                SlotInput input = inputs.get(i);
                if (input != null && ItemUtils.hasItemStack(player, input.getStack().copy())) {
                    ItemHandlerHelper.giveItemToPlayer(player, slot.getStack().copy());
                    ItemUtils.takeItemStack(player, input.getStack(), input.getStack().getCount());
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public int getSize() {
        if (merchant == null)
            return 0;
        return merchant.getTrades().size();
    }

    protected void addInput(SlotInput slot) {
        this.inputs.add(slot);
        addSlotToContainer(slot);
    }

    protected void addOutput(SlotOutput slot) {
        this.outputs.add(slot);
        addSlotToContainer(slot);
    }

}
