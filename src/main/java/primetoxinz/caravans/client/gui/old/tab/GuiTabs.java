package primetoxinz.caravans.client.gui.old.tab;

import com.google.common.collect.Lists;
import primetoxinz.caravans.api.Merchant;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class GuiTabs implements IDrawable {
    private GuiCaravan parent;
    private LinkedList<TabPage> pages = Lists.newLinkedList();
    private Button up, down;
    private int selected;


    public GuiTabs(GuiCaravan parent, List<Merchant> merchants, int tabX, int tabY, int menuX, int menuY) {
        this.parent = parent;

        List<Tab> tabs = merchants.stream().map(m -> new Tab(parent, m)).collect(Collectors.toList());
        int count = tabs.size() / 6;
        for (int i = 0; i <= count; i++) {
            int s = i * 6, e = Math.min(tabs.size(), s + 6);
            pages.add(new TabPage(Lists.newLinkedList(tabs.subList(s, e))));
        }
        selected = 0;
        up = new Button(parent, -27, 19, 28,12, 224, 83, 0, () -> selected = Math.max(0, selected - 1));
        down = new Button(parent, -27, 215, 28,12, 224, 71, 0, () -> selected = Math.min(pages.size()-1, selected + 1));
    }

    @Override
    public void draw(int x, int y, int mouseX, int mouseY) {

        up.draw(parent.getGuiLeft(), parent.getGuiTop(), mouseX, mouseY);
        down.draw(parent.getGuiLeft(), parent.getGuiTop(), mouseX, mouseY);
        pages.get(selected).draw(x, y, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        up.mouseReleased(mouseX, mouseY, state);
        down.mouseReleased(mouseX, mouseY, state);
        pages.get(selected).mouseReleased(mouseX,mouseY,state);
    }
}
