package me.kendricksellers.uhc.module;

import org.bukkit.event.Listener;

public interface ModuleListener extends Listener {

    default public void enable() {
    }
    default public void disable() {
    }
}
