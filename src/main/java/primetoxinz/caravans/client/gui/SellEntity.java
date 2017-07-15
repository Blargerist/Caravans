package primetoxinz.caravans.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import primetoxinz.caravans.CaravansMod;
import primetoxinz.caravans.api.ITradeEntity;
import primetoxinz.caravans.client.gui.slot.SlotInput;
import primetoxinz.caravans.common.trades.TradeEntity;
import primetoxinz.caravans.common.trades.TradeItemEntity;
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
        if (index < getTrades().size()) {
            ITradeEntity trade = getTrades().get(index);
            MessageEntityTrade message = new MessageEntityTrade(trade, container.caravaneer);
            if (message != null) {
                NetworkHandler.INSTANCE.sendToServer(message);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return !getTrades().isEmpty();
    }

    public List<ITradeEntity> getTrades() {
        return parent.container.merchant.getEntityTrades();
    }

    @Override
    public void init() {
        next = new Button(0, left() + 156, top() + 115, 12, 20, ">", this::next);
        prev = new Button(1, left() + 86, top() + 115, 12, 20, "<", this::prev);
        buy = new Button(2, left() + 106, top() + 115, 40, 20, "Buy", this::buyTrade);
        parent.addButton(next);
        parent.addButton(prev);
        parent.addButton(buy);

        if (getTrades().size() <= 1) {
            next.setEnabled(false);
            prev.setEnabled(false);
        }

    }

    public void next() {
        index = (index + 1) % getTrades().size();
    }

    public void prev() {
        index = index == 0 ? getTrades().size() - 1 : Math.max(0, index - 1);
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
        List<ITradeEntity> trades = container.merchant.getEntityTrades();
        if (!trades.isEmpty()) {

            ITradeEntity t = trades.get(index);
            GlStateManager.color(1, 1, 1, 1.0F);
            if (!t.isInStock()) {
                buy.setEnabled(false);
            }
            if (t instanceof TradeItemEntity) {
                sellingEntity = EntityUtil.createEntity(t.getOutput(), container.world);
                parent.drawEntityOnScreen(130, 64, 15, left() - mouseX, top() - mouseY, sellingEntity);

            } else if (t instanceof TradeEntity) {
                TradeEntity e = (TradeEntity) t;

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

            parent.drawHoveringText(I18n.translateToLocal(top ? "text.buy_entity" : "text.sell_entity") + " " + entity.getName(), mouseX, mouseY);
        }
    }


}

