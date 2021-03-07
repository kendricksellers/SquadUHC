package me.kendricksellers.uhc.modules;

import org.bukkit.event.Listener;

public interface ModuleListener extends Listener {

    default public void enable() {
    }
    default public void disable() {
    }
}
