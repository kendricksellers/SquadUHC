package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.event.MatchEndEvent;
import me.kendricksellers.uhc.event.MatchStartEvent;
import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.AdvancedModuleGUI;
import me.kendricksellers.uhc.module.gui.slot.NumberSlot;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;

public class TimeModule extends Module implements Runnable {

    private long gameStart, gameEnd;

    public TimeModule() {
        setName("Time");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.FIRE, 1, getName()));
        setGUI(new AdvancedModuleGUI(this, new ArrayList<>(Collections.singletonList(
                new NumberSlot(this, "Countdown", 20)
        ))));
        gameStart = 0;
        gameEnd = 0;
    }

    private int getCountdown() {
        return ((NumberSlot) this.findSlotByQuery("Countdown")).getValue();
    }

    @Override
    public void run() {

    }

    public static double getMatchTime() {
        TimeModule timeModule = (TimeModule) Match.getInstance().getModules().getModule("Time");
        if (Match.getInstance().isRunning()) {
            return (System.currentTimeMillis() - timeModule.getGameStart()) / 1000.0;

        }
        return (timeModule.getGameEnd() - timeModule.getGameStart());
    }

    @EventHandler
    public void onMatchStart(MatchStartEvent event) {
        gameStart = System.currentTimeMillis();
    }

    @EventHandler
    public void onMatchEnd(MatchEndEvent event) {
        gameEnd = System.currentTimeMillis();
    }

    public long getGameStart() {
        return gameStart;
    }

    public long getGameEnd() {
        return gameEnd;
    }
}
