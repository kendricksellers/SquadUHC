package me.kendricksellers.uhc.modules.module.scenario;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.modules.Module;

import me.kendricksellers.uhc.modules.ModuleType;
import me.kendricksellers.uhc.modules.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.modules.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.modules.gui.slot.NumberSlot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;


public class CutCleanModule extends Module {

    public CutCleanModule() {
        setName("CutClean");
        setType(ModuleType.SCENARIO);
        setEmblem(ItemUtils.createItemStack(Material.IRON_INGOT, 1, getName()));
        setGUI(new AdvancedModuleGUI(this, new ArrayList<>(Arrays.asList(
                new BooleanSlot(this, "Active", false),
                new NumberSlot(this, "Apple Rates", 25),
                new NumberSlot(this, "Flint Rates", 25)))));
        setEnabled(false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            if (event.getBlock().getType().equals(Material.IRON_ORE)) {
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
            }
        }
    }

    private int getAppleRates() {
        return ((NumberSlot) this.findSlotByQuery("Apple Rates")).getValue();
    }

    private int getFlintRates() {
        return ((NumberSlot) this.findSlotByQuery("Flint Rates")).getValue();
    }

}
