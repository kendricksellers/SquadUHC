package me.kendricksellers.uhc.modules.module.core;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.command.EndCommand;
import me.kendricksellers.uhc.command.StartCommand;
import me.kendricksellers.uhc.modules.Module;
import me.kendricksellers.uhc.modules.ModuleType;
import me.kendricksellers.uhc.command.OptionsCommand;
import me.kendricksellers.uhc.command.ScenariosCommand;
import me.kendricksellers.uhc.modules.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Bukkit;
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
    }

    private void loadCommand(String name, CommandExecutor executor) {
        SquadUHC.getInstance().getCommand(name).setExecutor(executor);
    }
}
