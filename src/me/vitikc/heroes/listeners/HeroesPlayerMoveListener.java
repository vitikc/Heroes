package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesPlayerMoveListener implements Listener {
    private HeroesAbilitiesManager abilitiesManager;


    public HeroesPlayerMoveListener(HeroesMain plugin){
        abilitiesManager = plugin.getAbilitiesManager();
    }

    @EventHandler
    public void onPlayerInDuelMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (!abilitiesManager.getLegionCommander().isPlayerInDuel(player)){
            return;
        }
        Double xTo = event.getTo().getX();
        Double xFrom = event.getFrom().getX();
        Double yTo = event.getTo().getY();
        Double yFrom = event.getFrom().getY();
        Double zTo = event.getTo().getZ();
        Double zFrom = event.getFrom().getZ();
        Location to = event.getFrom();
        to.setPitch(event.getTo().getPitch());
        to.setYaw(event.getTo().getYaw());
        if(event.getTo().locToBlock(xTo) != event.getFrom().locToBlock(xFrom) ||
                event.getTo().locToBlock(zTo) != event.getFrom().locToBlock(zFrom) ||
                event.getTo().locToBlock(yTo) != event.getFrom().locToBlock(yFrom)) {
            player.teleport(event.getFrom());
            event.setTo(to);
        }
    }
    @EventHandler
    public void onPlayerInStunMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (!abilitiesManager.getDragonKnight().getStunned().contains(player)){
            return;
        }
        player.sendMessage("You are stunned!");
        event.setCancelled(true);
    }
}
