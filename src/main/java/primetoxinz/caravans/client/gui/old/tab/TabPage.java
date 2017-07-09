package primetoxinz.caravans.client.gui.old.tab;

import com.google.common.collect.Lists;

import java.util.LinkedList;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class TabPage implements IDrawable {
    private LinkedList<Tab> tabs = Lists.newLinkedList();

    private Tab selected;

    public TabPage(LinkedList<Tab> tabs) {
        this.tabs = tabs;
        this.selected = tabs.getFirst();
    }


    @Override
    public void draw(int x, int y, int mouseX, int mouseY) {
        int y1 = y;
        for (Tab tab : tabs) {
            if (tab == selected)
                tab.setSelected(true);
            else
                tab.setSelected(false);
            tab.draw(x, y1 += 30, mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Tab tab : tabs) {
            if (state == 0 && tab.mouseOver(mouseX, mouseY)) {
                selected = tab;
            }
            tab.mouseReleased(mouseX, mouseY, state);
        }
    }
}
