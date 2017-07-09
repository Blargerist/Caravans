package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.client.gui.slot.SlotBase;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.client.gui.slot.SlotOutput;
import primetoxinz.caravans.common.entity.EntityCaravaneer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class GuiMerchant extends GuiContainer {

    public static final ResourceLocation CARAVAN_LOC = new ResourceLocation(CaravansMod.MODID, "textures/gui/caravan.png");

    protected ContainerMerchant container;
    protected ScrollBar scrollBar;
    protected List<TabMerchant> tabs = Lists.newArrayList();
    private static final int SHOW_COUNT = 6;

    public GuiMerchant(ContainerMerchant container) {
        super(container);
        this.container = container;
        if (container.merchant != null) {
            this.scrollBar = new ScrollBar(container.getSize(), this);
        }
        update();
        this.ySize = 256;
        tabs = container.caravan.getMerchants().stream().map(m -> new TabMerchant(this, m)).collect(Collectors.toList());
    }

    @Override
    public void onGuiClosed() {
        Caravan c = container.caravan;
        int i = c.getMerchants().indexOf(container.merchant);
        c.open = i;
        super.onGuiClosed();
    }

    @SideOnly(Side.CLIENT)
    public void update() {
        int c = scrollBar.getCurrent();
        int x = 8, y = 28;
        for (Slot slot : container.inventorySlots) {
            if (slot instanceof SlotBase)
                ((SlotBase) slot).setEnabled(false);
        }
        for (int i = c; i < Math.min(container.inputs.size(), c + SHOW_COUNT); i++) {
            SlotInput in = container.inputs.get(i);
            SlotOutput out = container.outputs.get(i);
            drawSlot(out, x + 36, y);
            drawSlot(in, x, y);
            y += 18;
        }
    }


    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CARAVAN_LOC);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        scrollBar.draw(68, 28, mouseX, mouseY);
        int x = i - 28, y = j + 30;
        for (TabMerchant tab : tabs) {
            tab.draw(x, y);
            if (tab.isMouseOver(mouseX, mouseY)) {
                drawHoveringText(tab.getName(), mouseX, mouseY);
            }
            y += 28;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        EntityCaravaneer entity = container.caravan.getEntity(container.merchant);
        if (entity != null) {
//            mc.fontRenderer.drawString(Integer.toString(entity.stay), 0, 0, 0x000000);
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        for (TabMerchant tab : tabs) {
            if (tab.isMouseOver(mouseX, mouseY)) {
                drawHoveringText(tab.getName(), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (TabMerchant tab : tabs) {
            if (tab.mouseReleased(mouseX, mouseY, state))
                break;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getDWheel();
        if (i != 0 && scrollBar != null && scrollBar.needed()) {
            i = i > 0 ? -1 : 1;
            scrollBar.move(i);
            update();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        update();
    }

    public void drawSlot(SlotBase slotIn, int x, int y) {
        slotIn.xPos = x;
        slotIn.yPos = y;
        slotIn.setEnabled(true);
    }


    public void drawItem(ItemStack stack, int x, int y) {
        GlStateManager.color(1F, 1F, 1F); //Forge: Reset color in case Items change it.
        GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
        zLevel = 100.0F;
        itemRender.zLevel = 100.0F;
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlays(this.fontRenderer, stack, x, y);
        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

}

