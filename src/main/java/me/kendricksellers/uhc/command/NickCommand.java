package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nick") && sender instanceof Player
                && sender.hasPermission("squaduhc.command.nick")) {
            PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");
            if (args.length > 0) {
                playerModule.getPlayer(((Player) sender).getUniqueId()).setDisplayName(String.join(" ", args));
                sender.sendMessage("Your new nickname is: " + String.join(" ", args));
                return true;
            }
            playerModule.getPlayer(((Player) sender).getUniqueId()).setDisplayName(sender.getName());
            sender.sendMessage("Your new nickname is: " + sender.getName());
        }
        return true;
    }
}
