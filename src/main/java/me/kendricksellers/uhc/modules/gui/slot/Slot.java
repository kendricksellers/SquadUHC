package me.kendricksellers.uhc.modules.gui.slot;

import me.kendricksellers.uhc.modules.Module;

public abstract class Slot {

    final private String query;
    final private Module module;

    public Slot() {
        this.query = "";
        this.module = null;
    }

    public Slot(Module module, String query) {
        this.query = query;
        this.module = module;
    }

    public String getQuery() {
        return query;
    }

    public Module getModule() {
        return module;
    }
}
