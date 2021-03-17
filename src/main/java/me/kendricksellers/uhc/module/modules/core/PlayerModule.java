package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.state.PlayerState;
import me.kendricksellers.uhc.util.ItemUtils;
import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.Material;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerModule extends Module {
    private List<UHCPlayer> players;

    public PlayerModule() {
        setName("Player");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.SKULL_ITEM, 1, getName()));
        setGUI(new ToggleModuleGUI(this));

        players = new ArrayList<>();

        setEnabled(true);
    }

    public List<UHCPlayer> getPlayers(PlayerState desiredState) {
        return this.players.stream().filter((player) -> player.getState() == desiredState).collect(Collectors.toList());
    }

    public List<UHCPlayer> getPlayers() {
        return this.players;
    }

    public void addPlayer(UHCPlayer player) {
        this.players.add(player);
    }

    public void setPlayerState(UUID playerUUID, PlayerState playerState) {
        UHCPlayer player = this.getPlayer(playerUUID);
        if(player != null) {
            player.setState(playerState);
        }
    }

    public boolean hasJoined(UUID uuid) {
        return this.players.stream().anyMatch(uhcPlayer -> uhcPlayer.getBukkitPlayer().getUniqueId() == uuid);
    }

    public UHCPlayer getPlayer(UUID uuid) {
        try {
            return this.players.stream().filter(player -> player.getBukkitPlayer().getUniqueId() == uuid).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
