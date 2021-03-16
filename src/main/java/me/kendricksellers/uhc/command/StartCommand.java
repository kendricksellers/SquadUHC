package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.SquadUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("start") && sender.hasPermission("squaduhc.command.start")) {
            // Add timer option
            SquadUHC.getInstance().getMatch().start();
        }
        return true;
    }
}
