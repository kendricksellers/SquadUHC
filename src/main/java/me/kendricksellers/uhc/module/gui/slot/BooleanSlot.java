package me.kendricksellers.uhc.module.gui.slot;

import me.kendricksellers.uhc.module.Module;
public class BooleanSlot extends Slot {

    final private String query;
    private boolean value;
    private final Module module;

    public BooleanSlot(Module module, String query, boolean value) {
        this.module = module;
        this.query = query;
        this.value = value;
    }

    public String getQuery() {
        return query;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public Module getModule() {
        return module;
    }
}
