package me.kendricksellers.uhc.modules.module.scenario;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.modules.Module;

import me.kendricksellers.uhc.modules.ModuleType;
import me.kendricksellers.uhc.modules.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.modules.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.modules.gui.slot.NumberSlot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class CutCleanModule extends Module {
    Map<EntityType, Material> mobDrops;
    List<Material> removeDrops;

    public CutCleanModule() {
        setName("CutClean");
        setType(ModuleType.SCENARIO);
        setEmblem(ItemUtils.createItemStack(Material.IRON_INGOT, 1, getName()));
        setGUI(new AdvancedModuleGUI(this, new ArrayList<>(Arrays.asList(
                new BooleanSlot(this, "Active", false),
                new NumberSlot(this, "Apple Rates", 25),
                new NumberSlot(this, "Flint Rates", 25)))));
        setEnabled(false);

        mobDrops = new HashMap<>();
        mobDrops.put(EntityType.COW, Material.COOKED_BEEF);
        mobDrops.put(EntityType.SHEEP, Material.COOKED_MUTTON);
        mobDrops.put(EntityType.PIG, Material.GRILLED_PORK);
        mobDrops.put(EntityType.CHICKEN, Material.COOKED_CHICKEN);

        removeDrops = Arrays.asList(Material.RAW_CHICKEN, Material.RAW_BEEF, Material.MUTTON, Material.PORK);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            Material type = event.getBlock().getType();

            if (type.equals(Material.IRON_ORE)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
            } else if(type.equals(Material.GOLD_ORE)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
            } else if(type.equals(Material.LEAVES) || type.equals(Material.LEAVES_2)) {
                double roll = Math.floor(Math.random() * 100);
                if(roll <= getAppleRates()) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
                }
            } else if(type.equals(Material.GRAVEL)) {
                double roll = Math.floor(Math.random() * 100);
                if(roll <= getFlintRates()) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FLINT));
                }
            }
        }
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        if (isEnabled() && Match.getInstance().isRunning()) {
            EntityType type = event.getEntityType();
            if(this.mobDrops.containsKey(type)) {
                event.getDrops().removeIf((item -> this.removeDrops.contains(item.getType())));
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(this.mobDrops.get(type)));
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
