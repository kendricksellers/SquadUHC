package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GenerateWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("generate") && sender.hasPermission("squaduhc.command.generate")
                && args.length == 0) {
            if (Match.getInstance().isGenerated()) {
                sender.sendMessage("The world has already been generated!");
                return true;
            }
            Match.getInstance().generateWorld();
        }
        return true;
    }
}
