package me.vitikc.heroes.listeners;

import me.vitikc.heroes.Heroes;
import me.vitikc.heroes.HeroesManager;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesItemListener implements Listener {
    private Heroes plugin;
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;

    public HeroesItemListener(Heroes plugin){
        this.plugin = plugin;
        heroesManager = plugin.getHeroesManager();
        abilityUtils = plugin.getAbilityUtils();
        abilitiesManager = plugin.getAbilitiesManager();
    }


    @EventHandler
    private void onItemUseEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        //if(!heroesManager.isSet(player)) return;
        if(player.getInventory().getItemInMainHand().getType() == Material.STICK) {
            player.sendMessage("1");
            Player target = abilityUtils.getTargetPlayer(player);
            if (target == null) {
                player.sendMessage("No players found");
                return;
            }
            target.sendMessage("you are target");
            abilitiesManager.BarbarianAttackAbility(player, target);
        } else if(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND){
            abilitiesManager.LegionCommanderAttackAbility(player);
        }
    }
}
