package me.kendricksellers.uhc.util;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UHCTeam {
    private UHCPlayer leader;
    private List<UHCPlayer> teamMembers;
    private List<UHCPlayer> invites;
    private final UUID teamID;
    private final ChatColor teamColor;

    public UHCTeam(UUID teamID, UHCPlayer leader, ChatColor teamColor) {
        this.teamID = teamID;
        this.teamColor = teamColor;
        this.leader = leader;
        teamMembers = new ArrayList<>();
        invites = new ArrayList<>();
        this.addTeamMember(this.leader);
    }

    public UUID getTeamID() {
        return this.teamID;
    }

    public ChatColor getTeamColor() {
        return this.teamColor;
    }

    public void addInvite(UHCPlayer player) {
        this.invites.add(player);
    }

    public boolean hasInvite(UHCPlayer player) {
        return this.invites.contains(player);
    }

    public void addTeamMember(UHCPlayer player) {
        this.teamMembers.add(player);
        this.invites.remove(player);
        player.setTeam(this);
    }

    public void revokeInvite(UHCPlayer player) {
        this.invites.remove(player);
    }

    public void removeTeamMember(UHCPlayer player) {
        teamMembers.remove(player);
        player.setTeam(null);
    }

    public List<UHCPlayer> getTeamMembers() {
        return this.teamMembers;
    }

    public int getMemberCount() {
        return this.teamMembers.size();
    }

    public UHCPlayer getLeader() {
        return this.leader;
    }

    public boolean isTeamMember(UHCPlayer uhcPlayer) {
        return uhcPlayer.isOnTeam(this);
    }

    public boolean isTeamLeader(UHCPlayer uhcPlayer) {
        return this.leader.equals(uhcPlayer);
    }

    public boolean isTeamLeader(String name) {
        return this.leader.getBukkitPlayer().getName().equalsIgnoreCase(name);
    }

    public boolean isTeamMember(Player bukkitPlayer) {
        PlayerModule module = (PlayerModule) Match.getInstance().getModules().getModule("Player");
        return this.isTeamMember(module.getPlayer(bukkitPlayer.getUniqueId()));
    }
}
