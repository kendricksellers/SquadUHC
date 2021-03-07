package me.kendricksellers.uhc.modules.gui.slot;

import me.kendricksellers.uhc.modules.Module;

public class NumberSlot extends Slot {

    final private String query;
    private int value;
    private final Module module;

    public NumberSlot(Module module, String query, int value) {
        this.module = module;
        this.query = query;
        this.value = value;
    }

    public String getQuery() {
        return query;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Module getModule() {
        return module;
    }
}
