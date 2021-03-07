package me.kendricksellers.uhc;

import me.kendricksellers.uhc.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class SquadUHC extends JavaPlugin {

    private static SquadUHC instance;
    private World lobby;
    private Match match;

    public static SquadUHC getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        lobby = Bukkit.getServer().getWorld("world");
        match = new Match();
    }

    @Override
    public void onDisable() {

    }

    public World getLobby() {
        return lobby;
    }

    public Match getMatch() {
        return match;
    }
}