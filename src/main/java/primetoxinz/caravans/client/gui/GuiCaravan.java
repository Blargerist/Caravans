package primetoxinz.caravans.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import primetoxinz.caravans.api.ICaravan;

/**
 * Created by primetoxinz on 7/2/17.
 */
public class GuiCaravan extends GuiContainer {

    private ICaravan caravan;

    public GuiCaravan(IItemHandlerModifiable player, ICaravan caravan, World world) {
        super(new ContainerCaravan(player, caravan, world));
        this.caravan = caravan;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
