package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.state.PlayerState;
import me.kendricksellers.uhc.util.ChatChannel;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.UHCMessage;
import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("o") || command.getName().equalsIgnoreCase("a")
                || command.getName().equalsIgnoreCase("t") || command.getName().equalsIgnoreCase("helpop")) {
            if (sender instanceof Server) {
                throw new CommandException("No console use");
            }

            if (args.length < 1) {
                ChatUtils.message((Player) sender, UHCMessage.COMMAND_CHAT_EMPTY);
                return true;
            }

            PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");
            UHCPlayer player = playerModule.getPlayer(((Player) sender).getUniqueId());
            PlayerState state = player.getState();

            if (command.getName().equalsIgnoreCase("o") && sender.hasPermission("squaduhc.chat.observer")) {
                if (state == PlayerState.DECEASED || state == PlayerState.OBSERVER || player.getBukkitPlayer().hasPermission("squaduhc.server.host")) {
                    ChatUtils.sendMessage(player, String.join(" ", args), ChatChannel.OBSERVER);
                    return true;
                }
                ChatUtils.message(player, UHCMessage.COMMAND_CHAT_OBS_ERR);
                return true;
            }
            if (command.getName().equalsIgnoreCase("t") && sender.hasPermission("squaduhc.chat.team")) {
                if (state != PlayerState.DECEASED) {
                    ChatUtils.sendMessage(player, String.join(" ", args), ChatChannel.TEAM);
                    return true;
                }
                ChatUtils.message(player, UHCMessage.COMMAND_CHAT_ALIVE_ERR);
                return true;
            }
            if (command.getName().equalsIgnoreCase("a") && sender.hasPermission("squaduhc.chat.admin")) {
                ChatUtils.sendMessage(player, String.join(" ", args), ChatChannel.ADMIN);
                return true;
            }
            if (command.getName().equalsIgnoreCase("helpop") && sender.hasPermission("squaduhc.command.helpop")) {
                ChatUtils.sendMessage(player, String.join(" ", args), ChatChannel.HELPOP);
                return true;
            }
        }
        return true;
    }
}
