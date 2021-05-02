package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.UHCMessage;
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
                ChatUtils.message((Player) sender, UHCMessage.COMMAND_NICK_NEW_NAME, String.join(" ", args));
                return true;
            }
            playerModule.getPlayer(((Player) sender).getUniqueId()).setDisplayName(sender.getName());
            ChatUtils.message((Player) sender, UHCMessage.COMMAND_NICK_NEW_NAME, sender.getName());
        }
        return true;
    }
}
