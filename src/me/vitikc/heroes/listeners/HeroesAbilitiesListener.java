package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesSamuraiAbilities;
import me.vitikc.heroes.events.HeroesStunEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Vitikc on 09/Feb/17.
 */
public class HeroesAbilitiesListener implements Listener {
    private HeroesAbilitiesManager abilitiesManager;

    public HeroesAbilitiesListener(HeroesMain plugin){
        abilitiesManager = plugin.getAbilitiesManager();
    }
    @EventHandler
    public void onPlayerStun(HeroesStunEvent event){
        Player target = event.getTarget();
        Player player = event.getPlayer();
        if (!HeroesSamuraiAbilities.getReflected().contains(target)) return;
        abilitiesManager.getDragonKnight().Stun(player);
        event.setCancelled(true);
    }

}
