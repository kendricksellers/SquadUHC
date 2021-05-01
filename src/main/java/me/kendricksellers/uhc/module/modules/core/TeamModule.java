package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.module.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.module.gui.slot.NumberSlot;
import me.kendricksellers.uhc.util.ItemUtils;
import me.kendricksellers.uhc.util.UHCPlayer;
import me.kendricksellers.uhc.util.UHCTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.*;

public class TeamModule extends Module {
    private final List<UHCTeam> teams;

    private final String CREATE_ALREADY_ON_TEAM = ChatColor.RED + "You are already on a team!";
    private final String INVITE_NOT_ON_TEAM = ChatColor.RED + "You are not on a team!";
    private final String INVITE_PLAYER_ON_TEAM = ChatColor.RED + "{0} is already on another team!";
    private final String INVITE_MSG = ChatColor.AQUA + "{0} has invited you to join their team! ";
    private final String JOIN_TEAM_JOINED = ChatColor.GREEN + "You have joined a team.";
    private final String LEAVE_TEAM_FAIL = ChatColor.RED + "You are not on a team!";
    private final String LEAVE_TEAM_SUCCESS = ChatColor.GREEN + "You have left your team!";
    private final String LEAVE_TEAM_DISBAND = ChatColor.RED + "Your team has been disbanded!";
    private final String NOT_TEAM_LEADER = ChatColor.RED + "Only the team leader can do this action!";
    private final String TEAM_CAPACITY_REACHED = ChatColor.RED + "This team is already full!";
    private final String SLOT_TEAM_SIZE = "Max Team Size";

    private final String TEAM_CREATE = ChatColor.GREEN + "Team has been created!";
    private final String TEAM_INVITED = ChatColor.GREEN + "{0} was invited to the team!";
    private final String TEAM_ACCEPTED = ChatColor.GREEN + "{0} joined the team!";
    private final String TEAM_LEFT = ChatColor.GREEN + "{0} left the team!";

    public TeamModule() {
        setName("Team");
        setType(ModuleType.OPTION);
        setEmblem(ItemUtils.createItemStack(Material.BANNER, 1, getName()));
        setGUI(new AdvancedModuleGUI(this, new ArrayList<>(Arrays.asList(
                new BooleanSlot(this, "Active", true),
                new NumberSlot(this, SLOT_TEAM_SIZE, 1)
        ))));

        this.teams = new ArrayList<>();

        setEnabled(true);
    }

    public void create(UHCPlayer player) {
        if(isEnabled()) {
            if(player.hasTeam()) {
                player.message(CREATE_ALREADY_ON_TEAM);
                return;
            }
            UUID uuid = UUID.randomUUID();
            UHCTeam team = new UHCTeam(uuid, player, ChatColor.AQUA /* Select random color */);
            teams.add(team);
            player.message(TEAM_CREATE);
        }
    }

    public void invite(UHCPlayer invitee, UHCPlayer invited) {
        if(isEnabled()) {
            if(!invitee.hasTeam()) {
                invitee.message(INVITE_NOT_ON_TEAM);
                return;
            }
            if(!invitee.getTeam().isTeamLeader(invitee)) {
                invitee.message(NOT_TEAM_LEADER);
                return;
            }
            if(invited.hasTeam() && invited.getTeam().equals(invitee.getTeam())) {
                invitee.message(ChatColor.AQUA + "Hey look around, see that guy? Yeah? He is on your team already.");
                return;
            }
            if(invited.hasTeam()) {
                invitee.message(INVITE_PLAYER_ON_TEAM.replace("{0}", invited.name()));
                return;
            }


            if(invitee.getTeam().getMemberCount() < this.getMaxTeamSize()) {
                //Send invite message? - Message Builder Util?
                //invitee has invited you to join their team. [accept]
                UHCTeam team = invitee.getTeam();
                if(!team.hasInvite(invited)) {
                    team.addInvite(invited);
                    invited.message(INVITE_MSG.replace("{0}", invitee.name()));
                    invitee.message(TEAM_INVITED.replace("{0}", invited.name()));
                }else {
                    invitee.message("You areleady dfinvidted thisd bperison stupodiun");
                }
            } else {
                invitee.message(TEAM_CAPACITY_REACHED);
            }
        }
    }

    public void joinTeam(UHCPlayer player, UHCTeam team) {
        if(isEnabled()) {
            if(!team.hasInvite(player)) {
                player.message("you don't have an invite to " + team.getLeader().name());
                return;
            }

            if(!player.hasTeam()) {
                if(team.getMemberCount() >= this.getMaxTeamSize()) {
                    player.message(TEAM_CAPACITY_REACHED);
                    return;
                }

                team.getTeamMembers().forEach(member -> {
                    member.message(TEAM_ACCEPTED.replace("{0}", player.name()));
                });

                Bukkit.broadcastMessage("A? " + team.getTeamMembers().size());
                team.addTeamMember(player);
                player.setNameColor(team.getTeamColor());
                player.message(JOIN_TEAM_JOINED);
                Bukkit.broadcastMessage("B? " + team.getTeamMembers().size());

            } else {
                player.message(CREATE_ALREADY_ON_TEAM);
            }
        }
    }

    public void leaveTeam(UHCPlayer player, UHCTeam team) {
        if(isEnabled()) {
            if(player.hasTeam() && player.isOnTeam(team)) {
                if(team.isTeamLeader(player)) {
                    this.disband(team);
                } else {
                    player.setNameColor(ChatColor.WHITE);
                    team.removeTeamMember(player);
                    player.message(LEAVE_TEAM_SUCCESS);
                    team.getTeamMembers().forEach(member -> {
                        member.message(TEAM_LEFT.replace("{0}", player.name()));
                    });
                }
            } else {
                player.message(LEAVE_TEAM_FAIL);
            }
        }
    }

    public void disband(UHCTeam team) {
        if(isEnabled()) {
            teams.remove(team);

            Bukkit.broadcastMessage("C? " + team.getTeamMembers().size());

            for (int i = team.getTeamMembers().size() - 1; i >= 0; i--) {
                UHCPlayer player = team.getTeamMembers().get(i);
                team.removeTeamMember(player);
                Bukkit.broadcastMessage("NAME?! " + player.name());
                player.message(LEAVE_TEAM_DISBAND);
            }
        }
    }

    public UHCTeam getTeam(String leader) {
        for(UHCTeam team : this.getTeams()) {
            if(team.isTeamLeader(leader)) {
                return team;
            }
        }

        return null;
    }

    public List<UHCTeam> getTeams() {
        if(isEnabled()) {
            return this.teams;
        }
        return new ArrayList<>();
    }

    private int getMaxTeamSize() {
        return ((NumberSlot) this.findSlotByQuery(SLOT_TEAM_SIZE)).getValue();
    }
}
