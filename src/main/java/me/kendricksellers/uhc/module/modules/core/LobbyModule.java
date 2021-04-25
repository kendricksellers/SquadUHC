package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.state.MatchState;
import me.kendricksellers.uhc.state.PlayerState;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.util.ItemUtils;
import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
        if(!isEnabled()) { return; }

        if (Match.getInstance().getState() == MatchState.PREGAME) {
            managePlayerJoin(event.getPlayer(), PlayerState.COMBATANT);
        } else {
            managePlayerJoin(event.getPlayer(), PlayerState.OBSERVER);
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
        if (isEnabled() && event.getPlayer().getWorld() == lobby) {
            event.setCancelled(true);
        }
    }

    private void managePlayerJoin(Player player, PlayerState state) {
        try {
            PlayerModule playerModule = ((PlayerModule) Match.getInstance().getModules().getModule("Player"));

            if (!playerModule.hasJoined(player.getUniqueId())) {
                playerModule.addPlayer(new UHCPlayer(player, state));
            }
            player.teleport(lobby.getSpawnLocation());
        } catch (ModuleNotFoundException e) {
            e.printStackTrace();
        }
    }
}
