package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.module.modules.core.TeamModule;
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
    private final String PLAYER_NOT_FOUND = ChatColor.RED + "Player \"{0}\" was not found!";
    private final String PLAYER_TEAM_NOT_FOUND = ChatColor.RED + "{0}'s team was not found!";
    private final String PLAYER_HAS_NO_TEAM = ChatColor.RED + "You are not on a team!";
    private final String JOIN_MATCH_STARTED = ChatColor.RED + "The match has already started! You can not use this command!";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            throw new CommandException("Sender must be instanceof player.");
        }

        if(Match.getInstance().isRunning()) {
            sender.sendMessage(JOIN_MATCH_STARTED);
            return true;
        }


        Player player = (Player) sender;


        if(command.getName().equalsIgnoreCase("team")) {
            if(args.length == 0) {
                return false;
            }

            try {
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
                            uhcPlayer.message("/team invite <player>");
                            return true;
                        }

                        bukkitPlayer = Bukkit.getPlayer(subArgs[0]);
                        if(bukkitPlayer == null || !bukkitPlayer.isOnline()) {
                            uhcPlayer.message(PLAYER_NOT_FOUND.replace("{0}", subArgs[0]));
                            return true;
                        }

                        teamModule.invite(uhcPlayer, playerModule.getPlayer(bukkitPlayer.getUniqueId()));
                        break;
                    case "accept":
                        if(subArgs.length != 1) {
                            uhcPlayer.message("/team accept <player>");
                            return true;
                        }
                        team = teamModule.getTeam(subArgs[0]);
                        if(team == null) {
                            uhcPlayer.message(PLAYER_TEAM_NOT_FOUND.replace("{0}", subArgs[0]));
                            return true;
                        }
                        teamModule.joinTeam(uhcPlayer, team);
                        break;
                    case "leave":
                        if(uhcPlayer.hasTeam()) {
                            teamModule.leaveTeam(uhcPlayer, uhcPlayer.getTeam());
                        } else {
                            uhcPlayer.message(PLAYER_HAS_NO_TEAM);
                        }
                        break;
                    case "revoke":
                        if(subArgs.length != 1) {
                            uhcPlayer.message("/team invite <player>");
                            return true;
                        }

                        bukkitPlayer = Bukkit.getPlayer(subArgs[0]);
                        if(bukkitPlayer == null || !bukkitPlayer.isOnline()) {
                            uhcPlayer.message(PLAYER_NOT_FOUND.replace("{0}", subArgs[0]));
                            return true;
                        }

                        team = uhcPlayer.getTeam();
                        UHCPlayer revoked = playerModule.getPlayer(bukkitPlayer.getUniqueId());
                        if(team.hasInvite(revoked)) {
                            team.revokeInvite(revoked);
                            uhcPlayer.message("{0}'s invite revoked".replace("{0}", revoked.name()));
                        } else {
                            uhcPlayer.message("{0} doesn't have an invite!".replace("{0}", revoked.name()));
                        }

                    default:
                        return false;
                }
            } catch (ModuleNotFoundException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
