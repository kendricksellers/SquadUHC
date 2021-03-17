package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.exception.InvalidGUIException;
import me.kendricksellers.uhc.module.gui.GUIGenerator;
import me.kendricksellers.uhc.module.gui.ModuleGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OptionsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            throw new CommandException("Sender must be instanceof player.");
        }
        if (command.getName().equals("options")) {
            ModuleGUI gui = null;
            try {
                gui = GUIGenerator.getInstance().createGUI(ModuleType.OPTION);
            } catch (InvalidGUIException e) {
                e.printStackTrace();
            }
            if (gui != null) {
                ((Player) sender).openInventory(gui.getInv());
            }
        }
        return true;
    }
}
