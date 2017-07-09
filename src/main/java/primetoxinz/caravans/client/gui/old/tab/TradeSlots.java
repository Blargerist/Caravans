package primetoxinz.caravans.client.gui.old.tab;

/**
 * Created by primetoxinz on 7/6/17.
 */
public class TradeSlots {

    private SlotStack input, output;

    public TradeSlots(SlotStack input, SlotStack output) {
        this.input = input;
        this.output = output;
    }

    public SlotStack getInput() {
        return input;
    }

    public SlotStack getOutput() {
        return output;
    }
}
