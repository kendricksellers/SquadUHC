package me.kendricksellers.uhc.match;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.match.world.WorldGeneration;
import me.kendricksellers.uhc.modules.Module;
import me.kendricksellers.uhc.modules.ModuleList;
import me.kendricksellers.uhc.modules.module.core.CommandModule;
import me.kendricksellers.uhc.modules.module.core.LobbyModule;
import me.kendricksellers.uhc.modules.module.option.PermadayModule;
import me.kendricksellers.uhc.modules.module.scenario.CutCleanModule;
import me.kendricksellers.uhc.states.MatchState;
import me.kendricksellers.uhc.states.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Match {

    private static Match instance;
    private World world;
    private ModuleList<Module> modules;
    private MatchState state;
    private Map<UUID, PlayerState> players;

    public Match() {
        instance = this;
        world = WorldGeneration.getInstance().generateWorld();
        players = new HashMap<>();
        modules = new ModuleList<>();
        state = MatchState.PREGAME;
        loadModules();
    }

    public static Match getInstance() {
        if (instance == null) {
            instance = new Match();
        }
        return instance;
    }

    public World getWorld() {
        return world;
    }

    public ModuleList<Module> getModules() {
        return modules;
    }

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public boolean isRunning() {
        return state == MatchState.STARTED;
    }

    public Map<UUID, PlayerState> getPlayers() {
        return players;
    }

    public void loadModules() {
        // CORE
        modules.add(new CommandModule());
        modules.add(new LobbyModule());

        // OPTIONS
        modules.add(new PermadayModule());

        // SCENARIOS
        modules.add(new CutCleanModule());
    }

    public void start() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
        }
        state = MatchState.STARTED;
    }

    public void end(Player winner) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.teleport(SquadUHC.getInstance().getLobby().getSpawnLocation());
        }
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN + winner.getName() + " has won!");
    }

}
