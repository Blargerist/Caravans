package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import primetoxinz.caravans.api.Caravan;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class ContainerCaravan extends Container {
    private Caravan caravan;
    private IItemHandlerModifiable playerInventory;
    private World world;
    private List<MerchantTab> merchantTabs = Lists.newArrayList();

    public ContainerCaravan(IItemHandlerModifiable playerInventory, Caravan caravan, World world) {
        this.caravan = caravan;
        this.playerInventory = playerInventory;
        this.world = world;
        setupPlayerInventory();
        setupTradingInventory();
    }

    public MerchantTab getTab(int i) {
        if (merchantTabs.isEmpty())
            return null;
        return merchantTabs.get(i);
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


    public void setupTradingInventory() {
        merchantTabs.addAll(caravan.getMerchants().stream().map(m -> new MerchantTab(m,8,10)).collect(Collectors.toList()));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }


    public List<MerchantTab> getTabs() {
        return merchantTabs;
    }
}
