package me.kendricksellers.uhc.util;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.state.PlayerState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUtils {

    public static String createGlobalMessage(UHCPlayer sender, String message) {
        // Will probably change to better format later
        return ChatColor.AQUA + "[G] " + getMessageFormat(sender) + message;
    }

    public static String createTeamMessage(UHCPlayer sender, String message) {
        return ChatColor.GREEN + "[T] " + getMessageFormat(sender) + message;
    }

    public static String createHelpOpMessage(UHCPlayer sender, String message) {
        return ChatColor.RED + "[H] " + getMessageFormat(sender) + message;
    }

    public static String createObserverMessage(UHCPlayer sender, String message) {
        return ChatColor.GRAY + "[O] " + getMessageFormat(sender) + message;
    }

    public static String createAdminMessage(UHCPlayer sender, String message) {
        return ChatColor.GOLD + "[A] " + getMessageFormat(sender) + message;
    }

    public static String getMessageFormat(UHCPlayer player) {
        String ret = "";
        if (player.getBukkitPlayer().hasPermission("squaduhc.server.host")) {
            ret += ChatColor.GOLD + "*";
        }
        if (player.getBukkitPlayer().hasPermission("squaduhc.server.kendrick")) {
            ret += ChatColor.DARK_PURPLE + "*" + ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.AQUA + ": " + ChatColor.RED;
            return ret;
        }
        return ret + ChatColor.AQUA + player.getDisplayName() + ": " + ChatColor.WHITE;
    }

    public static void sendMessage(UHCPlayer sender, String message, ChatChannel channel) {
        PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");

        List<UHCPlayer> onlinePlayers = playerModule.getPlayers().stream().filter(p -> p.getBukkitPlayer().isOnline()).collect(Collectors.toList());

        if (channel == ChatChannel.GLOBAL) {
            Bukkit.broadcastMessage(createGlobalMessage(sender, message));
        } else if (channel == ChatChannel.TEAM) {
            // HANDLE TEAM CHAT HERE
        } else if (channel == ChatChannel.ADMIN) {
            onlinePlayers.stream().filter(p -> p.getBukkitPlayer().hasPermission("squaduhc.chat.admin")).forEach(p ->
                p.getBukkitPlayer().sendMessage(createAdminMessage(sender, message))
            );
        } else if (channel == ChatChannel.OBSERVER) {
            onlinePlayers.stream().filter(p ->
                    p.getState() == PlayerState.OBSERVER || p.getState() == PlayerState.DECEASED
                            || p.getBukkitPlayer().hasPermission("squaduhc.server.host")
            ).forEach(p -> {
                p.getBukkitPlayer().sendMessage(createObserverMessage(sender, message));
            });
        } else if (channel == ChatChannel.HELPOP) {
            onlinePlayers.stream().filter(p ->
                    p.getBukkitPlayer().hasPermission("squaduhc.chat.helpop.receive")
            ).forEach(p -> {
                p.getBukkitPlayer().sendMessage(createHelpOpMessage(sender, message));
            });
        }
    }

    public static void message(UHCPlayer player, UHCMessage message, Object... args) {
        message(player.getBukkitPlayer(), message, args);
    }

    public static void message(Player player, UHCMessage message, Object... args) {
        player.sendMessage(message.get(player, args));
    }
}
