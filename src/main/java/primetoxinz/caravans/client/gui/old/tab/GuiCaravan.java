package primetoxinz.caravans.client.gui.old.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.lwjgl.input.Mouse;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.Caravan;
import primetoxinz.caravans.client.gui.ScrollBar;

import java.awt.*;
import java.io.IOException;


/**
 * Created by primetoxinz on 7/2/17.
 */
public class GuiCaravan extends GuiContainer {

    public static final ResourceLocation CARAVAN_LOC = new ResourceLocation(CaravansMod.MODID, "textures/gui/caravan.png");

    public EntityPlayer player;
    private Caravan caravan;
    private World world;
    private ContainerCaravan container;

    private GuiTabs tabs;
    public ScrollBar scrollBar;

    public GuiCaravan(IItemHandlerModifiable inventory, EntityPlayer player, Caravan caravan, World world) {
        this(player, new ContainerCaravan(inventory, caravan, player, world));
        this.caravan = caravan;
        this.world = world;
    }

    private GuiCaravan(EntityPlayer player, ContainerCaravan container) {
        super(container);
        this.player = player;
        this.container = container;
        this.ySize = 256;
    }

    @Override
    public void initGui() {
        super.initGui();
        tabs = new GuiTabs(this, container.getMerchants(), 0, 0, 100, 0);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        tabs.draw(this.guiLeft - 28, this.guiTop + 4, mouseX, mouseY);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CARAVAN_LOC);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        FontRenderer f = Minecraft.getMinecraft().fontRenderer;
        f.drawString(caravan.toString(), guiLeft, guiTop, Color.GRAY.getRGB());

    }

    public void drawItemStack(ItemStack stack, int x, int y, String altText) {
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
    public void drawHoveringText(String text, int x, int y) {
        super.drawHoveringText(text, x, y);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        tabs.mouseReleased(mouseX, mouseY, state);
    }

    public void setZ(float z) {
        this.zLevel = z;
    }

    public float getZ() {
        return this.zLevel;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

    }
}
