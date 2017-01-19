package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Vitikc on 11/Jan/17.
 */
public class HeroesDamageListener implements Listener {
    private HeroesMain plugin;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;


    public HeroesDamageListener(HeroesMain plugin){
        this.plugin = plugin;
        abilitiesManager = plugin.getAbilitiesManager();
        abilityUtils = plugin.getAbilityUtils();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        //switch () heroes
        if (abilityUtils.isCounterAttackProc(30))
            abilitiesManager.getBarbarian().Defense(target);
        //if() non cooldown
        abilitiesManager.getBarbarian().Ultimate(damager, target);
        if (abilityUtils.isCounterAttackProc(50)){
            abilitiesManager.getLegionCommander().Defense(target,damager,event.getDamage());
        }
    }
    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event){
        if (event.getDamager().getType() != EntityType.ARROW) return;
        if (!(event.getEntity() instanceof Player)) return;
        Arrow arrow = (Arrow) event.getDamager();
        if (arrow.getCustomName() == null) return;
        Player player = (Player) event.getEntity();
        if (arrow.getCustomName() == player.getDisplayName()) {
            event.setCancelled(true);
            plugin.getLogger().info(arrow.getCustomName());
            return;
        }
        Player owner = plugin.getServer().getPlayer(arrow.getCustomName());
        if(owner == null) return;
        abilityUtils.setSpeed(owner, 6);
    }
}
