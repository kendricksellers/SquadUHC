package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.module.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class WorldModule extends Module {

    public WorldModule() {
        setName("World");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.GRASS, 1, getName()));
        setGUI(new AdvancedModuleGUI(this, new ArrayList<>(Collections.singletonList( // Add more biomebase changes here
                new BooleanSlot(this, "Default", true)
        ))));

        setEnabled(true);
    }

    public World generateWorld() { // Any edits needed to be done to the world - REMOVE JUNGLE, ETC
        WorldCreator wc = new WorldCreator(UUID.randomUUID().toString());
        if (getDefault()) {
            // Edit BiomeBase[]? Can't access net.minecraft for some reason?
        }
        return wc.createWorld();
    }

    private boolean getDefault() {
        return ((BooleanSlot) this.findSlotByQuery("Default")).getValue();
    }
}
