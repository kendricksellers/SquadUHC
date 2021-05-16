package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.module.gui.slot.BooleanSlot;
import me.kendricksellers.uhc.module.gui.slot.NumberSlot;
import me.kendricksellers.uhc.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.*;

public class TeamModule extends Module {
    private final List<UHCTeam> teams;
    private final String SLOT_TEAM_SIZE = "Max Team Size";

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
                ChatUtils.message(player, UHCMessage.COMMAND_CREATE_ON_TEAM);
                return;
            }
            UUID uuid = UUID.randomUUID();
            UHCTeam team = new UHCTeam(uuid, player, ChatColor.AQUA /* Select random color */);
            teams.add(team);
            ChatUtils.message(player, UHCMessage.COMMAND_TEAM_CREATE);
        }
    }

    public void invite(UHCPlayer invitee, UHCPlayer invited) {
        if(isEnabled()) {
            if(!invitee.hasTeam()) {
                ChatUtils.message(invitee, UHCMessage.COMMAND_INVITE_NO_TEAM);
                return;
            }
            if(!invitee.getTeam().isTeamLeader(invitee)) {
                ChatUtils.message(invitee, UHCMessage.COMMAND_MISC_NOT_LEADER);
                return;
            }
            if(invited.hasTeam() && invited.getTeam().equals(invitee.getTeam())) {
                ChatUtils.message(invitee, UHCMessage.COMMAND_INVITE_PLAYER_YOUR_TEAM, invited.name());
                return;
            }
            if(invited.hasTeam()) {
                ChatUtils.message(invitee, UHCMessage.COMMAND_INVITE_PLAYER_OTHER_TEAM, invited.name());
                return;
            }

            if(invitee.getTeam().getMemberCount() < this.getMaxTeamSize()) {
                //Send invite message? - Message Builder Util?
                //invitee has invited you to join their team. [accept]
                UHCTeam team = invitee.getTeam();
                if(!team.hasInvite(invited)) {
                    team.addInvite(invited);
                    ChatUtils.message(invited, UHCMessage.COMMAND_INVITE_INVITED, team.getLeader().name());
                    ChatUtils.message(invitee, UHCMessage.COMMAND_TEAM_INVITED, invited.name());
                }else {
                    ChatUtils.message(invitee, UHCMessage.COMMAND_INVITE_PENDING, invited.name());
                }
            } else {
                ChatUtils.message(invitee, UHCMessage.COMMAND_MISC_TEAM_LIMIT);
            }
        }
    }

    public void joinTeam(UHCPlayer player, UHCTeam team) {
        if(isEnabled()) {
            if(!team.hasInvite(player)) {
                ChatUtils.message(player, UHCMessage.COMMAND_INVITE_NO_INVITE, team.getLeader().name());
                return;
            }

            if(!player.hasTeam()) {
                if(team.getMemberCount() >= this.getMaxTeamSize()) {
                    ChatUtils.message(player, UHCMessage.COMMAND_MISC_TEAM_LIMIT);
                    return;
                }

                team.getTeamMembers().forEach(member -> {
                    ChatUtils.message(member, UHCMessage.COMMAND_TEAM_JOINED, player.name());
                });

                team.addTeamMember(player);
                player.setNameColor(team.getTeamColor());
                ChatUtils.message(player, UHCMessage.COMMAND_JOIN_JOINED, team.getLeader().name());
            } else {
                ChatUtils.message(player, UHCMessage.COMMAND_CREATE_ON_TEAM);
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
                    ChatUtils.message(player, UHCMessage.COMMAND_LEAVE_SUCCESS);

                    team.getTeamMembers().forEach(member -> {
                        ChatUtils.message(member, UHCMessage.COMMAND_TEAM_LEFT, player.name());
                    });
                }
            } else {
                ChatUtils.message(player, UHCMessage.COMMAND_LEAVE_FAIL);
            }
        }
    }

    public void disband(UHCTeam team) {
        if(isEnabled()) {
            teams.remove(team);

            for (int i = team.getTeamMembers().size() - 1; i >= 0; i--) {
                UHCPlayer player = team.getTeamMembers().get(i);
                team.removeTeamMember(player);
                ChatUtils.message(player, UHCMessage.COMMAND_LEAVE_DISBAND);
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
