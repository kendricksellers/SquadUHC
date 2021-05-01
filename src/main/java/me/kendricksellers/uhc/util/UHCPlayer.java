package me.kendricksellers.uhc.util;

import me.kendricksellers.uhc.state.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UHCPlayer {
    private final Player bukkitPlayer;
    private UHCTeam team;
    private String displayName;
    private PlayerState state;
    private final Map<String, Object> metadata;


    public UHCPlayer(Player bukkitPlayer, PlayerState state) {
        this.bukkitPlayer = bukkitPlayer;
        this.state = state;
        this.team = null;
        this.metadata = new HashMap<>();
        this.displayName = bukkitPlayer.getDisplayName();
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState() {
        return state;
    }

    public void setTeam(UHCTeam team) {
        this.team = team;
    }

    public UHCTeam getTeam() {
        return this.team;
    }

    public boolean isOnTeam(UHCTeam other) {
        return  this.team != null &&
                this.team.getTeamID().equals(other.getTeamID());
    }

    public String name() {
        return this.bukkitPlayer.getName();
    }

    public boolean hasTeam() {
        return this.team != null;
    }

    public void setMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    public boolean hasMetaData(String key) {
        return this.metadata.containsKey(key);
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }

    public void message(String message) {
        this.getBukkitPlayer().sendMessage(message);
    }

    public void setNameColor(ChatColor color) {
        getBukkitPlayer().setDisplayName(color + getBukkitPlayer().getName());
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
