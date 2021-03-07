package me.kendricksellers.uhc.modules.gui;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.modules.Module;
import me.kendricksellers.uhc.modules.ModuleList;
import me.kendricksellers.uhc.modules.ModuleType;
import me.kendricksellers.uhc.modules.exception.InvalidGUIException;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GUIGenerator {
    private static GUIGenerator instance;
    final private ModuleList<Module> modules;

    public GUIGenerator() {
        instance = this;
        modules = SquadUHC.getInstance().getMatch().getModules();
    }

    public static GUIGenerator getInstance() {
        if (instance == null) {
            instance = new GUIGenerator();
        }
        return instance;
    }

    public ModuleGUI createGUI(ModuleType type) throws InvalidGUIException {
        ArrayList<ItemStack> items = new ArrayList<>();
        modules.stream().filter(module -> module.getType() == type).collect(Collectors.toList())
                .forEach(m -> {
                    items.add(m.getEmblem());
                });
        int size_ = ((items.size() / 7) + 3) * 9;
        if (size_ > 81) {
            // implement multiple pages
            throw new InvalidGUIException("There are more gui items than supported for type: " + type.toString());
        }
        return new ModuleGUI(type.toString(), size_, items);
    }
}
