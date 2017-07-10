package primetoxinz.caravans.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.client.gui.slot.SlotBase;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class GuiMerchant extends GuiContainer {

    public static final ResourceLocation CARAVAN_LOC = new ResourceLocation(CaravansMod.MODID, "textures/gui/caravan.png");

    protected ContainerMerchant container;

    protected List<TabMerchant> tabs = Lists.newArrayList();
    protected List<GuiBase> GUIs = Lists.newArrayList();

    public GuiMerchant(ContainerMerchant container) {
        super(container);
        this.container = container;
        this.GUIs.add(new SellItem(this));
        this.GUIs.add(new SellEntity(this));

        this.ySize = 256;
        tabs = container.caravan.getMerchants().stream().map(m -> new TabMerchant(this, m)).collect(Collectors.toList());
    }

    public GuiButton addButton(GuiButton button) {
        return super.addButton(button);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CARAVAN_LOC);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        int x = i - 28, y = j + 30;
        for (TabMerchant tab : tabs) {
            tab.draw(x, y);
            y += 28;
        }
        for (GuiBase g : GUIs)
            g.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        for (TabMerchant tab : tabs) {
            if (tab.isMouseOver(mouseX, mouseY)) {
                drawHoveringText(tab.getName(), mouseX - guiLeft, mouseY - guiTop);
            }
        }
        for (GuiBase g : GUIs)
            g.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (TabMerchant tab : tabs) {
            if (tab.mouseReleased(mouseX, mouseY, state))
                break;
        }
        for (GuiBase g : GUIs)
            g.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        for (GuiBase g : GUIs)
            g.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiBase g : GUIs)
            g.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
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

    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }


}

