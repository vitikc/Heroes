package me.vitikc.heroes.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Vitikc on 09/Feb/17.
 */
public class HeroesStunEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean canceled = false;
    private Player target;
    private Player player;


    public HeroesStunEvent(Player p, Player t){
        this.player = p;
        this.target = t;
    }

    public Player getPlayer(){
        return this.player;
    }
    public Player getTarget(){
        return this.target;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.canceled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
