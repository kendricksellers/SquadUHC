package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nick") && sender instanceof Player
                && sender.hasPermission("squaduhc.command.nick") && args.length > 0) {
            try {
                PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");
                playerModule.getPlayer(((Player) sender).getUniqueId()).setDisplayName(String.join(" ", args));
                sender.sendMessage("Your new nickname is: " + String.join(" ", args));
            } catch (ModuleNotFoundException e) {
                e.printStackTrace();
                throw new CommandException("PlayerModule unable to locate player.");
            }
        }
        return true;
    }
}
