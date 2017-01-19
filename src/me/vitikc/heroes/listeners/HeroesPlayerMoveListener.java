package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
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
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (!abilitiesManager.getLegionCommander().isPlayerInDuel(player)){
            return;
        }
        event.setCancelled(true);
    }
}
