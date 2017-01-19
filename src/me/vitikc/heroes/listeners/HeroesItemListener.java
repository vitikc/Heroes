package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
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
    private HeroesMain plugin;
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;

    public HeroesItemListener(HeroesMain plugin){
        this.plugin = plugin;
        heroesManager = plugin.getHeroesManager();
        abilityUtils = plugin.getAbilityUtils();
        abilitiesManager = plugin.getAbilitiesManager();
    }


    @EventHandler
    private void onItemUseEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        //if(!heroesManager.isSet(player)) return;
        //switch heroes
        if(player.getInventory().getItemInMainHand().getType() == Material.STICK) {
            player.sendMessage("1");
            Player target = abilityUtils.getTargetPlayer(player);
            if (target == null) {
                player.sendMessage("No players found");
                return;
            }
            target.sendMessage("you are target");
            abilitiesManager.getBarbarian().Attack(player, target);
        } else if(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND){
            abilitiesManager.getLegionCommander().Attack(player);
        } else if (player.getInventory().getItemInMainHand().getType() == Material.COAL){
            Player target = abilityUtils.getTargetPlayer(player);
            if (target == null) {
                player.sendMessage("No players found");
                return;
            }
            abilitiesManager.getLegionCommander().Ultimate(player, target);
            player.sendMessage("DUEL START");
        }
    }
}
