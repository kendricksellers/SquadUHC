package me.kendricksellers.uhc.module;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.module.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.module.gui.GUI;
import me.kendricksellers.uhc.module.gui.slot.Slot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class Module implements ModuleListener {

    public static Module instance;
    private String name;
    private boolean enabled;
    private ModuleType type;
    private ItemStack emblem;
    private GUI gui;

    public Module() {
        instance = this;
        Bukkit.getServer().getPluginManager().registerEvents(this, SquadUHC.getInstance());
    }

    public static Module getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            enable();
        } else {
            disable();
        }
        ItemUtils.updateGUILore(emblem, enabled);
    }

    public ModuleType getType() {
        return type;
    }

    public void setType(ModuleType type) {
        this.type = type;
    }

    public ItemStack getEmblem() {
        return emblem;
    }

    public void setEmblem(ItemStack emblem) {
        this.emblem = emblem;
    }

    public GUI getGUI() {
        return gui;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void enable() {
        Bukkit.broadcastMessage(ChatColor.GREEN + name + " has been enabled!");
    }

    @Override
    public void disable() {
        Bukkit.broadcastMessage(ChatColor.RED + name + " has been disabled!");
    }

    // For finding slots by query in Advanced Module GUIs
    public Slot findSlotByQuery(String query) {
        if (gui instanceof AdvancedModuleGUI) {
            AdvancedModuleGUI moduleGUI = (AdvancedModuleGUI) gui;
            ArrayList<Slot> slots = moduleGUI.getSlots();
            for (int i = 0; i < slots.size(); i++) {
                if (slots.get(i).getQuery().equals(query)) {
                    return slots.get(i);
                }
            }
        }
        return null;
    }
}
