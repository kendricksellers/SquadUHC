package me.kendricksellers.uhc.modules.module.core;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.modules.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.states.MatchState;
import me.kendricksellers.uhc.states.PlayerState;
import me.kendricksellers.uhc.modules.Module;
import me.kendricksellers.uhc.modules.ModuleType;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;

public class LobbyModule extends Module {

    final private World lobby;

    public LobbyModule() {
        setName("Lobby");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.BRICK, 1, getName()));
        setGUI(new ToggleModuleGUI(this));

        lobby = SquadUHC.getInstance().getLobby();
        //lobby.setSpawnLocation(200, 70, 200); // Set to actual world spawn

        setEnabled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (isEnabled() && Match.getInstance().getState() == MatchState.PREGAME) {
            Map<UUID, PlayerState> players = Match.getInstance().getPlayers();
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            if (!players.containsKey(uuid)) {
                players.put(uuid, PlayerState.COMBATANT);
            }
            player.teleport(lobby.getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (isEnabled() && Match.getInstance().getState() == MatchState.PREGAME) {
            if (event.getEntityType().equals(EntityType.PLAYER) && event.getEntity().getWorld() == lobby) {
                event.setCancelled(true); // Will need to change on implementation of pvp arena
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (isEnabled() && event.getEntity().getWorld() == lobby) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isEnabled()  && event.getPlayer().getWorld() == lobby) {
            event.setCancelled(true);
        }
    }
}
