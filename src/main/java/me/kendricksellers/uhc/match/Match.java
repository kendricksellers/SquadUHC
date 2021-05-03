package me.kendricksellers.uhc.match;

import me.kendricksellers.uhc.SquadUHC;
import me.kendricksellers.uhc.event.MatchEndEvent;
import me.kendricksellers.uhc.event.MatchStartEvent;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleList;
import me.kendricksellers.uhc.module.modules.core.CommandModule;
import me.kendricksellers.uhc.module.modules.core.LobbyModule;
import me.kendricksellers.uhc.module.modules.core.PlayerModule;
import me.kendricksellers.uhc.module.modules.core.TeamModule;
import me.kendricksellers.uhc.module.modules.core.*;
import me.kendricksellers.uhc.module.modules.option.PermadayModule;
import me.kendricksellers.uhc.module.modules.scenario.CutCleanModule;
import me.kendricksellers.uhc.module.modules.scenario.NoAnvilModule;
import me.kendricksellers.uhc.state.MatchState;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.UHCMessage;
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

    public Match() {
        instance = this;
        modules = new ModuleList<>();
        state = MatchState.PREGAME;
        loadModules();

        world = null;
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
        modules.add(new ChatModule());
        modules.add(new CommandModule());
        modules.add(new LobbyModule());
        modules.add(new PlayerModule());
        modules.add(new TeamModule());
        modules.add(new WorldModule());

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

        UHCPlayer player = ((PlayerModule) getModules().getModule("Player")).getPlayer(winner.getUniqueId());
        Bukkit.getPluginManager().callEvent(new MatchEndEvent(player));

        Bukkit.getOnlinePlayers().forEach(p -> {
            ChatUtils.message(p, UHCMessage.WIN_MESSAGE, winner.getName());
        });
    }

    public void generateWorld() {
        world = ((WorldModule) this.getModules().getModule("World")).generateWorld();
    }

    public boolean isGenerated() {
        return world != null;
    }

}
