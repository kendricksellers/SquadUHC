package me.kendricksellers.uhc.match.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.UUID;

public class WorldGeneration {

    private static WorldGeneration instance;

    public WorldGeneration() {
    }

    public static WorldGeneration getInstance() {
        if (instance == null) {
            instance = new WorldGeneration();
        }
        return instance;
    }

    public World generateWorld() { // Any edits needed to be done to the world - REMOVE JUNGLE, ETC
        WorldCreator wc = new WorldCreator(UUID.randomUUID().toString());
        return wc.createWorld();
    }
}
