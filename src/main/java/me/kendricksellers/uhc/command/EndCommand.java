package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.SquadUHC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("end") && sender.hasPermission("squaduhc.command.end") && args.length == 1) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            SquadUHC.getInstance().getMatch().end(player);
        }
        return true;
    }
}
