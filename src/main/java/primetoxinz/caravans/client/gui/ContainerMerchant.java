package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.api.Merchant;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.client.gui.slot.SlotOutput;
import primetoxinz.caravans.common.ItemUtils;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.util.List;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class ContainerMerchant extends Container {

    protected Caravan caravan;
    protected EntityPlayer player;
    public EntityCaravaneer caravaneer;
    protected InventoryMerchantItem inventoryMerchantItem;
    protected InventoryMerchantItemEntity inventoryMerchantItemEntity;
    protected Merchant merchant;
    protected IItemHandlerModifiable playerInventory;

    protected World world;

    protected List<SlotInput> inputs = Lists.newArrayList();
    protected List<SlotOutput> outputs = Lists.newArrayList();

    protected List<SlotInput> entityInputs = Lists.newArrayList();


    public ContainerMerchant(Caravan caravan, Merchant merchant, EntityPlayer player, EntityCaravaneer caravaneer) {
        this.caravan = caravan;
        this.world = player.world;
        this.merchant = merchant;
        this.playerInventory = (IItemHandlerModifiable) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        this.player = player;
        this.caravaneer = caravaneer;
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
            this.inventoryMerchantItem = new InventoryMerchantItem(merchant.getItemTrades());
            int size = inventoryMerchantItem.getSize();
            for (int i = 0; i < size; i++) {
                addInput(new SlotInput(inventoryMerchantItem, i, 0, 0));
                addOutput(new SlotOutput(inventoryMerchantItem, i, 36, 0, merchant));
            }

            this.inventoryMerchantItemEntity = new InventoryMerchantItemEntity(merchant.getItemEntityTrades());
            size = inventoryMerchantItemEntity.getSize();
            for (int i = 0; i < size; i++) {
                addEntityInput(new SlotInput(inventoryMerchantItemEntity, i, 0, 0));
            }
        }

    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        BlockPos pos = caravan.getPosition();
        return merchant != null && (pos != null && playerIn.getDistanceSq(pos) < 16);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId < 36) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        } else {
            Slot slot = inventorySlots.get(slotId);
            if (slot instanceof SlotOutput) {
                int i = outputs.indexOf(slot);
                SlotOutput output = (SlotOutput) slot;
                SlotInput input = inputs.get(i);
                if (output.getTrade().isInStock() && input != null && ItemUtils.hasItemStack(player, input.getStack().copy())) {
                    ItemHandlerHelper.giveItemToPlayer(player, slot.getStack().copy());
                    ItemUtils.takeItemStack(player, input.getStack(), input.getStack().getCount());
                    if (merchant != null) {
                        output.getTrade().onTrade(world, caravaneer);
                        player.addExperience(CaravansMod.ConfigHandler.experience);
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    protected void addInput(SlotInput slot) {
        this.inputs.add(slot);
        addSlotToContainer(slot);
    }

    protected void addEntityInput(SlotInput slot) {
        this.entityInputs.add(slot);
        addSlotToContainer(slot);
    }

    protected void addOutput(SlotOutput slot) {
        this.outputs.add(slot);
        addSlotToContainer(slot);
    }


}
