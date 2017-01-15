package me.vitikc.heroes.listeners;

import me.vitikc.heroes.Heroes;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import sun.plugin2.message.HeartbeatMessage;

/**
 * Created by Vitikc on 11/Jan/17.
 */
public class HeroesDamageListener implements Listener {
    private Heroes plugin;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;


    public HeroesDamageListener(Heroes plugin){
        this.plugin = plugin;
        abilitiesManager = plugin.getAbilitiesManager();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        //switch () heroes
        if (abilityUtils.isCounterAttackProc(30))
            abilitiesManager.BarbarianDefenseAbility(target);
        //if() non cooldown
        abilitiesManager.BarbarianUltimateAbility(damager, target);
    }
    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event){
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
