package me.kendricksellers.uhc.module.modules.scenario;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.ItemUtils;
import me.kendricksellers.uhc.util.UHCMessage;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class NoAnvilModule extends Module {
    public NoAnvilModule() {
        setName("NoAnvil");
        setType(ModuleType.SCENARIO);
        setEmblem(ItemUtils.createItemStack(Material.ANVIL, 1, getName()));
        setGUI(new ToggleModuleGUI(this));
        setEnabled(false);
    }

    @EventHandler
    public void onAnvilCraft(PrepareItemCraftEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            if (event.getRecipe().getResult().getType().equals(Material.ANVIL)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));

                for (HumanEntity entity : event.getViewers()) {
                    if (entity instanceof Player) {
                        ChatUtils.message((Player) entity, UHCMessage.ANVIL_USE_DENY);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onAnvilPlace(BlockPlaceEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            if(event.getBlock().getType().equals(Material.ANVIL)) {
                event.setCancelled(true);
                ChatUtils.message(event.getPlayer(), UHCMessage.ANVIL_USE_DENY);
            }
        }
    }

    @EventHandler
    public void onAnvilClick(InventoryOpenEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            if(event.getInventory().getType().equals(InventoryType.ANVIL)) {
                event.setCancelled(true);
                ChatUtils.message((Player) event.getPlayer(), UHCMessage.ANVIL_USE_DENY);
            }
        }
    }
}
