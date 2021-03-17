package me.kendricksellers.uhc.module.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class GUI implements Listener {

    private final Inventory inv;

    public GUI() {
        inv = Bukkit.createInventory(null, 9, "Default");
    }

    public GUI(String name, int size) {
        inv = Bukkit.createInventory(null, size, name);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inv)) {
            event.setCancelled(true);
        }
    }

    public Inventory getInventory() {
        return this.inv;
    }
}
