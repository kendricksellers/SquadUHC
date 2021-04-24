package me.kendricksellers.uhc.match;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.event.MatchEndEvent;
import me.kendricksellers.uhc.event.MatchStartEvent;
import me.kendricksellers.uhc.match.world.WorldGeneration;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleList;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.modules.core.CommandModule;
import me.kendricksellers.uhc.module.modules.core.LobbyModule;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.module.modules.option.PermadayModule;
import me.kendricksellers.uhc.module.modules.scenario.CutCleanModule;
import me.kendricksellers.uhc.module.modules.scenario.NoAnvilModule;
import me.kendricksellers.uhc.state.MatchState;
import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Match {

    private static Match instance;
    private World world;
    private ModuleList<Module> modules;
    private MatchState state;
    private final String WIN_MESSAGE = ChatColor.GREEN + "{0} is the winner!";

    public Match() {
        instance = this;
        world = WorldGeneration.getInstance().generateWorld();
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

    public void loadModules() {
        // CORE
        modules.add(new CommandModule());
        modules.add(new LobbyModule());
        modules.add(new PlayerModule());

        // OPTIONS
        modules.add(new PermadayModule());

        // SCENARIOS
        modules.add(new CutCleanModule());
        modules.add(new NoAnvilModule());
    }

    public void start() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
        }
        Bukkit.getPluginManager().callEvent(new MatchStartEvent());

        state = MatchState.STARTED;
    }

    public void end(Player winner) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.teleport(SquadUHC.getInstance().getLobby().getSpawnLocation());
        }

        try {
            UHCPlayer player = ((PlayerModule) getModules().getModule("Player")).getPlayer(winner.getUniqueId());
            Bukkit.getPluginManager().callEvent(new MatchEndEvent(player));
        } catch (ModuleNotFoundException e) {
            e.printStackTrace();
        }
        Bukkit.getServer().broadcastMessage(WIN_MESSAGE.replace("{0}", winner.getName()));
    }

}
