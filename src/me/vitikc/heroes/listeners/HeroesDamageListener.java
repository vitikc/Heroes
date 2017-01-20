package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroTypes;
import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.HeroesManager;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
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
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;
    private HeroesManager heroesManager;
    public HeroesDamageListener(HeroesMain plugin){
        this.plugin = plugin;
        abilitiesManager = plugin.getAbilitiesManager();
        abilityUtils = plugin.getAbilityUtils();
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
        heroesManager = plugin.getHeroesManager();
    }
    @EventHandler
    public void addPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (!abilitiesManager.getLegionCommander().isEarnedDamage(player)){
            return;
        }
        event.setDamage(event.getDamage() + abilitiesManager.getLegionCommander().getDamage(player));
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        if (!heroesManager.isSet(target))
            return;
        HeroTypes type = heroesManager.getPlayerHero(target);
        switch (type){
            case BARBARIAN:
                if (abilityUtils.isCounterAttackProc(30))
                    abilitiesManager.getBarbarian().Defense(target);
                break;
            case LEGION_COMMANDER:
                if (abilityUtils.isCounterAttackProc(50)){
                    abilitiesManager.getLegionCommander().Defense(target,damager,event.getDamage());
                }
                break;
            default:
                break;
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
