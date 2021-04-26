package me.kendricksellers.uhc.module.gui;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ModuleGUI extends GUI {

    private final Inventory inv;

    public ModuleGUI(String name, int size, ArrayList<ItemStack> items) {
        inv = Bukkit.createInventory(null, size, name);
        updateInventory(items);
        Bukkit.getServer().getPluginManager().registerEvents(this, SquadUHC.getInstance());
    }

    public void updateInventory(ArrayList<ItemStack> items) {
        if (items.size() > inv.getSize()) {
            throw new ArrayIndexOutOfBoundsException(
                    inv.getName() + " inventory size is not appropriate: " + items.size() + " > " + inv.getSize()
            );
        }
        int itemsAdded = 0;
        for (int row = 0; row < (inv.getSize() / 9); row++) {
            for (int col = 0; col < 9; col++) {
                int slot = (row * 9) + (col % 9);
                if (row == 0 || row == ((inv.getSize() / 9) - 1) || col == 0 || col == 8) {
                    inv.setItem(slot, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 7, ""));
                    continue;
                }
                if (itemsAdded == items.size()) {
                    continue;
                }
                inv.setItem(slot, items.get(itemsAdded));
                itemsAdded++;
            }
        }
    }

    public Inventory getInv() {
        return this.inv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inv)) {
            if (SquadUHC.getInstance().getMatch().getModules().contains(event.getCurrentItem().getItemMeta().getDisplayName())) {
                Module module = Match.getInstance().getModules().getModule(event.getCurrentItem().getItemMeta().getDisplayName());
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(module.getGUI().getInventory());
            }
            event.setCancelled(true);
        }
    }
}
