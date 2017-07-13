package primetoxinz.caravans.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.IEntityTrade;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.common.EntityTrade;
import primetoxinz.caravans.common.ItemEntityTrade;
import primetoxinz.caravans.common.entity.EntityUtil;
import primetoxinz.caravans.network.MessageEntityTrade;
import primetoxinz.caravans.network.NetworkHandler;

import java.awt.*;
import java.util.List;

/**
 * Created by primetoxinz on 7/9/17.
 */
public class SellEntity extends GuiBase {

    public static final ResourceLocation SELL_LOC = new ResourceLocation(CaravansMod.MODID, "textures/gui/entity_slot.png");
    private int index = 0;
    private Button next, prev, buy;

    public SellEntity(GuiMerchant parent) {
        super(parent);

    }

    public void buyTrade() {
        System.out.println("buy");
        if (index < getTrades().size()) {
            IEntityTrade trade = getTrades().get(index);
            MessageEntityTrade message = null;
            if (trade instanceof ItemEntityTrade) {
                message = new MessageEntityTrade((ItemStack) trade.getInput(), trade.getOutput().getCanonicalName());
            } else if (trade instanceof EntityTrade) {
                message = new MessageEntityTrade(((Class) trade.getInput()).getCanonicalName(), ((Class) trade.getOutput()).getCanonicalName());
            }
            if(message != null) {
                NetworkHandler.INSTANCE.sendToServer(message);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return !getTrades().isEmpty();
    }

    public List<IEntityTrade> getTrades() {
        return parent.container.merchant.getEntityTrades();
    }

    @Override
    public void init() {
        next = new Button(0, left() + 156, top() + 115, 12, 20, ">", () -> index = (index + 1) % getTrades().size());
        prev = new Button(1, left() + 86, top() + 115, 12, 20, "<", () -> index = index == 0 ? getTrades().size() - 1 : Math.max(0, index - 1));
        buy = new Button(2, left() + 106, top() + 115, 40, 20, "Buy", this::buyTrade);
        parent.addButton(next);
        parent.addButton(prev);
        parent.addButton(buy);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawEntityTooltip(mouseX, mouseY, sellingEntity, true);
        drawEntityTooltip(mouseX, mouseY, buyingEntity, false);
    }

    @Override
    public void update() {
        List<SlotInput> inputs = parent.container.entityInputs;
        if (index < inputs.size()) {
            buyingEntity = null;
            SlotInput input = inputs.get(index);
            mc().getTextureManager().bindTexture(SELL_LOC);

            parent.drawSlot(input, 119, 79);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (next.mousePressed(mc(), mouseX, mouseY)) {
            next.clicked();
        }
        if (prev.mousePressed(mc(), mouseX, mouseY)) {
            prev.clicked();
        }
        if (buy.mousePressed(mc(), mouseX, mouseY)) {
            buy.clicked();
        }
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int i = left();
        int j = top();
        mc().getTextureManager().bindTexture(SELL_LOC);
        if (sellingEntity != null) {
            drawTexturedModalRect(i + 85, j + 27, 0, 0, 84, 43);
        }
        if (buyingEntity != null) {
            drawTexturedModalRect(i + 85, j + 70, 0, 0, 84, 43);
        } else {
            drawTexturedModalRect(left() + 85, top() + 70, 0, 42, 84, 43);
        }

    }

    private EntityLiving sellingEntity, buyingEntity;

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        List<IEntityTrade> trades = container.merchant.getEntityTrades();
        if (!trades.isEmpty()) {
            IEntityTrade t = trades.get(index);
            if (t instanceof ItemEntityTrade) {
                sellingEntity = EntityUtil.createEntity(t.getOutput(), container.world);
                parent.drawEntityOnScreen(130, 64, 15, left() - mouseX, top() - mouseY, sellingEntity);
            } else if (t instanceof EntityTrade) {
                EntityTrade e = (EntityTrade) t;

                sellingEntity = EntityUtil.createEntity(e.getOutput(), container.world);
                buyingEntity = EntityUtil.createEntity(e.getInput(), container.world);

                parent.drawEntityOnScreen(130, 64, 15, left() - mouseX, top() - mouseY, sellingEntity);
                parent.drawEntityOnScreen(130, 100, 15, left() - mouseX, top() - mouseY, buyingEntity);
            } else {
                sellingEntity = null;
                buyingEntity = null;
            }

        }
    }


    public void drawEntityTooltip(int mouseX, int mouseY, EntityLiving entity, boolean top) {
        if (entity != null && new Rectangle(left() + 85, top() + (top ? 27 : 70), 84, 43).contains(mouseX, mouseY)) {
            parent.drawHoveringText(entity.getName(), mouseX, mouseY);
        }
    }


}
