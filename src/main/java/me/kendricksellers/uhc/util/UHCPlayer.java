package me.kendricksellers.uhc.util;

import me.kendricksellers.uhc.state.PlayerState;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UHCPlayer {
    private final Player bukkitPlayer;
    private PlayerState state;
    private final Map<String, Object> metadata;


    public UHCPlayer(Player bukkitPlayer, PlayerState state) {
        this.bukkitPlayer = bukkitPlayer;
        this.state = state;
        this.metadata = new HashMap<>();
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

    public void setMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    public boolean hasMetaData(String key) {
        return this.metadata.containsKey(key);
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }
}
