package me.kendricksellers.uhc.modules.gui;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.modules.Module;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ToggleModuleGUI extends GUI {
    final private Module module;
    final private Inventory inv;

    public ToggleModuleGUI(Module module) {
        this.module = module;
        Bukkit.getServer().getPluginManager().registerEvents(this, SquadUHC.getInstance());
        inv = Bukkit.createInventory(null, 27, module.getName());
        updateInventory();
    }

    public Module getModule() {
        return module;
    }

    private void updateInventory() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotNum = (row * 9) + (col % 9);
                if (row == 0 || row == 2 || col == 0 || col == 8) {
                    inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 7, ""));
                } else if (col == 2) {
                    inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 14, "Disable"));
                } else if (col == 4) {
                    inv.setItem(slotNum, module.getEmblem());
                } else if (col == 6) {
                    inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 5, "Enable"));
                }
            }
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inv)) {
            String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
            if (event.getWhoClicked().hasPermission("squaduhc.module.toggle")) {
                if (displayName.equals("Enable") && !module.isEnabled()) {
                    module.setEnabled(true);
                } else if (displayName.equals("Disable") && module.isEnabled()) {
                    module.setEnabled(false);
                }
                updateInventory();
            }
            event.setCancelled(true);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}
