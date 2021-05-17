package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.module.modules.core.TeamModule;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.UHCMessage;
import me.kendricksellers.uhc.util.UHCPlayer;
import me.kendricksellers.uhc.util.UHCTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeamCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            throw new CommandException("Sender must be instanceof player.");
        }

        Player player = (Player) sender;
        if(Match.getInstance().isRunning()) {
            ChatUtils.message(player, UHCMessage.COMMAND_MISC_MATCH_STARTED);
            return true;
        }

        if(command.getName().equalsIgnoreCase("team")) {
            if(args.length == 0) {
                return false;
            }

            String subCmd = args[0];
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            TeamModule teamModule = (TeamModule) Match.getInstance().getModules().getModule("Team");
            PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");
            UHCPlayer uhcPlayer = playerModule.getPlayer(player.getUniqueId());
            UHCTeam team;
            Player bukkitPlayer;

            switch (subCmd) {
                case "create":
                    teamModule.create(uhcPlayer);
                    break;
                case "invite":
                    if(subArgs.length != 1) {
                        uhcPlayer.getBukkitPlayer().sendMessage("/team invite <player>");
                        return true;
                    }

                    bukkitPlayer = Bukkit.getPlayer(subArgs[0]);
                    if(bukkitPlayer == null || !bukkitPlayer.isOnline()) {
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_MISC_PLAYER_NOT_FOUND, subArgs[0]);
                        return true;
                    }

                    teamModule.invite(uhcPlayer, playerModule.getPlayer(bukkitPlayer.getUniqueId()));
                    break;
                case "accept":
                    if(subArgs.length != 1) {
                        uhcPlayer.getBukkitPlayer().sendMessage("/team accept <player>");

                        return true;
                    }
                    team = teamModule.getTeam(subArgs[0]);
                    if(team == null) {
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_MISC_TEAM_NOT_FOUND);
                        return true;
                    }
                    teamModule.joinTeam(uhcPlayer, team);
                    break;
                case "leave":
                    if(uhcPlayer.hasTeam()) {
                        teamModule.leaveTeam(uhcPlayer, uhcPlayer.getTeam());
                    } else {
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_LEAVE_FAIL);
                    }
                    break;
                case "revoke":
                    if(subArgs.length != 1) {
                        uhcPlayer.getBukkitPlayer().sendMessage("/team invite <player>");
                        return true;
                    }

                    bukkitPlayer = Bukkit.getPlayer(subArgs[0]);
                    if(bukkitPlayer == null || !bukkitPlayer.isOnline()) {
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_MISC_PLAYER_NOT_FOUND, subArgs[0]);
                        return true;
                    }

                    team = uhcPlayer.getTeam();
                    UHCPlayer revoked = playerModule.getPlayer(bukkitPlayer.getUniqueId());
                    if(team.hasInvite(revoked)) {
                        team.revokeInvite(revoked);
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_TEAM_REVOKE, revoked.name());
                    } else {
                        ChatUtils.message(uhcPlayer, UHCMessage.COMMAND_TEAM_REVOKE_NONE, revoked.name());
                    }

                default:
                    return false;
            }
        }
        return true;
    }
}
