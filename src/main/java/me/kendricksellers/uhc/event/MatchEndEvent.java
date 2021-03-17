package me.kendricksellers.uhc.event;

import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MatchEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UHCPlayer winner;

    public MatchEndEvent(UHCPlayer winner) {
        this.winner = winner;
    }

    public UHCPlayer getWinner() {
        return this.winner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
