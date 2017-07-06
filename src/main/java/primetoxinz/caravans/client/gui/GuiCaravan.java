package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.Caravan;

import java.awt.*;
import java.util.List;


/**
 * Created by primetoxinz on 7/2/17.
 */
public class GuiCaravan extends GuiContainer {
    public static final ResourceLocation CARAVAN_LOC = new ResourceLocation(CaravansMod.MODID, "textures/gui/caravan.png");

    private Caravan caravan;
    private World world;
    private ContainerCaravan container;
    private MerchantTab selected;
    private int tabIndex;
    private int scrollIndex;
    private final int SCROLL_SIZE = 6, TAB_COUNT = 6;
    private Button up, down;

    public GuiCaravan(IItemHandlerModifiable player, Caravan caravan, World world) {
        this(new ContainerCaravan(player, caravan, world));
        this.caravan = caravan;
        this.world = world;
    }

    private GuiCaravan(ContainerCaravan container) {
        super(container);
        this.container = container;
        this.selected = this.container.getTab(0);
        this.ySize = 256;
        this.scrollIndex = 0;
        this.tabIndex = 0;

    }

    @Override
    public void initGui() {
        super.initGui();
        addButton(up = new Button(0, this.guiLeft - 28, this.guiTop + 18, 28, 20, "▲", this::tabUp));
        addButton(down = new Button(1, this.guiLeft - 28, this.guiTop + this.ySize - 46, 28, 20, "▼", this::tabDown));
    }

    public void tabUp() {
        int prev = tabIndex - TAB_COUNT;
        tabIndex = Math.max(0, prev);
        selected = this.container.getTab(tabIndex);
    }

    public void tabDown() {
        int size = container.getTabs().size();
        int next = tabIndex + TAB_COUNT;
        if (next < size)
            tabIndex = Math.min(size, next);
        selected = this.container.getTab(tabIndex);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (selected != null) {
            drawTrades(selected);
        }
        drawTabTooltip(mouseX, mouseY);
        if (tabIndex <= TAB_COUNT) {

        }
    }

    public void drawTabTooltip(int mouseX, int mouseY) {
        if (isPointInRegion(-28, 18, 28, this.height, mouseX, mouseY)) {
            List<String> list = Lists.newArrayList();
            for (MerchantTab tab : container.getTabs()) {
                if (tab.isClicked(mouseX, mouseY)) {
                    list.add(tab.merchant.getLocalizedName());
                }
            }
            this.drawHoveringText(list, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CARAVAN_LOC);
        int i = this.guiLeft;
        int j = this.guiTop;

        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        if (needScrollbar()) {
            drawTexturedModalRect(i + 68, j + 28, 232, 0, 12, 15);
        }

        if (container.getTabs().size() > 0) {
            drawTabs();
        }
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        FontRenderer f = Minecraft.getMinecraft().fontRenderer;
        f.drawString(caravan.toString(), guiLeft, guiTop, Color.GRAY.getRGB());

    }

    public boolean needScrollbar() {
        if (selected == null)
            return false;
        return selected.slots.size() > SCROLL_SIZE;
    }

    private void drawTabs() {
        int w = 28, h = 28;
        int i = this.guiLeft;
        int j = this.guiTop + 40;
        int size = container.getTabs().size();
        List<MerchantTab> tabs = container.getTabs().subList(tabIndex, Math.min(size, tabIndex + TAB_COUNT));
        for (int m = tabIndex; m < tabs.size(); m++) {
            MerchantTab tab = container.getTab(m);
            GlStateManager.disableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(CARAVAN_LOC);
            int offsetTextureX = 0;
            if (selected == tab) {
                offsetTextureX = 28;
            }
            int tabX = i - w;
            int tabY = j + ((m - tabIndex) * 28);
            tab.setTabPos(tabX, tabY);
            this.drawTexturedModalRect(tabX, tabY, 224, 15 + offsetTextureX, 32, 28);
            drawItemStack(tab.merchant.getIcon(), tabX + 8, tabY + 6, "");
        }
    }

    private void drawTrades(MerchantTab tab) {
        int size = tab.slots.size();
        List<SlotTrade> slots = tab.slots.subList(this.scrollIndex, Math.min(size, this.scrollIndex + SCROLL_SIZE));
        for (SlotTrade slot : slots) {
            drawTrade(slot);
        }
    }

    private void drawTrade(SlotTrade slot) {
        drawItemStack(slot.getInput(), slot.getX() + guiLeft, slot.getY() + guiTop, "");
        drawItemStack(slot.getOutput(), slot.getX() + 36 + guiLeft, slot.getY() + guiTop, "");
    }

    private void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRenderer;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
    }

    @Override
    protected boolean handleComponentClick(ITextComponent component) {

        return super.handleComponentClick(component);
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (state == 0) {
            for (MerchantTab tab : container.getTabs()) {
                if (tab.isClicked(mouseX, mouseY)) {
                    this.selected = tab;
                    break;
                }
            }
        }
    }
}
