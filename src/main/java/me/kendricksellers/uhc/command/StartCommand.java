package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.match.Match;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("start") && sender.hasPermission("squaduhc.command.start")) {
            if (!Match.getInstance().isGenerated()) {
                sender.sendMessage("You must generate the world prior to starting the match.");
                return true;
            }
            // Add timer option
            SquadUHC.getInstance().getMatch().start();
        }
        return true;
    }
}
