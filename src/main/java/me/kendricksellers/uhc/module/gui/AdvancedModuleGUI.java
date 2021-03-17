package me.kendricksellers.uhc.module.gui;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.module.gui.slot.NumberSlot;
import me.kendricksellers.uhc.module.gui.slot.Slot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AdvancedModuleGUI extends GUI {

    final private Module module;
    final private Inventory inv;
    final private ArrayList<Slot> slots;

    public AdvancedModuleGUI(Module module, ArrayList<Slot> slots) {
        this.module = module;
        Bukkit.getServer().getPluginManager().registerEvents(this, SquadUHC.getInstance());
        inv = Bukkit.createInventory(null, 18 + (9 * slots.size()), module.getName());
        this.slots = slots;
        updateInventory();
    }

    public Module getModule() {
        return module;
    }

    private void updateInventory() {
        int slotsPos = 0;
        for (int row = 0; row < slots.size() + 2; row++) {
            for (int col = 0; col < 9; col++) {
                int slotNum = (row * 9) + (col % 9);
                if (col == 0 || col == 8 || row == 0 || row == slots.size() + 1) {
                    inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 7, ""));
                    continue;
                }
                if (col == 2 || col == 4 || col == 6) {
                    if (slots.get(slotsPos) instanceof BooleanSlot) {
                        BooleanSlot slot = (BooleanSlot) slots.get(slotsPos);
                        String[] lore = { slot.getQuery(), "Current Value: " + Boolean.toString(slot.getValue()) };
                        if (col == 2) {
                            inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 14, "Disable", lore));
                            continue;
                        } else if (col == 6) {
                            inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 5, "Enable", lore));
                            slotsPos++;
                            continue;
                        }
                        inv.setItem(slotNum, ItemUtils.createItemStack(Material.BANNER, 1, slot.getQuery(), lore));
                    } else if (slots.get(slotsPos) instanceof NumberSlot) {
                        NumberSlot slot = (NumberSlot) slots.get(slotsPos);
                        String[] lore = { slot.getQuery(), "Current Value: " + Integer.toString(slot.getValue()) };
                        if (col == 2) {
                            inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 14, "Decrease", lore));
                            continue;
                        } else if (col == 6) {
                            inv.setItem(slotNum, ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 5, "Increase", lore));
                            slotsPos++;
                            continue;
                        }
                        inv.setItem(slotNum, ItemUtils.createItemStack(Material.BANNER, 1, slot.getQuery(), lore));
                    }
                }
            }
        }
    }

    public ArrayList<Slot> getSlots() {
        return this.slots;
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inv)) {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.AIR)
                    || event.getCurrentItem().equals(ItemUtils.createItemStack(Material.STAINED_GLASS, 1, (short) 7, ""))) {
                return;
            }
            ItemStack item = event.getCurrentItem();
            String displayName = item.getItemMeta().getDisplayName();
            if (displayName.equals("Enable")) {
                BooleanSlot slot = (BooleanSlot) module.findSlotByQuery(item.getItemMeta().getLore().get(0));
                if(!slot.getValue()) {
                    slot.setValue(true);
                    module.setEnabled(true);
                }
            } else if (displayName.equals("Disable")) {
                BooleanSlot slot = (BooleanSlot) module.findSlotByQuery(item.getItemMeta().getLore().get(0));
                if (slot.getValue()) {
                    slot.setValue(false);
                    module.setEnabled(false);
                }
            } else if (displayName.equals("Increase")) {
                NumberSlot slot = (NumberSlot) module.findSlotByQuery(item.getItemMeta().getLore().get(0));
                slot.setValue(slot.getValue() + 1);
            } else if (displayName.equals("Decrease")) {
                NumberSlot slot = (NumberSlot) module.findSlotByQuery(item.getItemMeta().getLore().get(0));
                slot.setValue(slot.getValue() - 1);
            }
            updateInventory();
            event.setCancelled(true);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inv;
    }
}
