package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.command.*;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;

public class CommandModule extends Module {

    public CommandModule() {
        setName("Command");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.STICK, 1, getName()));
        setGUI(new ToggleModuleGUI(this));

        loadCommands();
        setEnabled(true);
    }

    private void loadCommands() {
        // Register command executors here
        // Don't forget to put commands in plugin.yml!
        loadCommand("end", new EndCommand());
        loadCommand("options", new OptionsCommand());
        loadCommand("scenarios", new ScenariosCommand());
        loadCommand("start", new StartCommand());
        loadCommand("team", new TeamCommands());
    }

    private void loadCommand(String name, CommandExecutor executor) {
        SquadUHC.getInstance().getCommand(name).setExecutor(executor);
    }
}
