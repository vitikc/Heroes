package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroTypes;
import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.entity.HeroesDragon;
import me.vitikc.heroes.entity.HeroesGolem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * Created by Vitikc on 06/Feb/17.
 */
public class HeroesDragonListener implements Listener {
    private HeroesMain plugin;
    private HeroesAbilitiesManager abilities;

    public HeroesDragonListener(HeroesMain plugin){
        this.plugin = plugin;
        this.abilities = plugin.getAbilitiesManager();
    }


    @EventHandler
    public void onDragonPassBlock(EntityExplodeEvent event){
        Entity e = event.getEntity();
        if (e.getCustomName()==null)
            return;
        String d;
        String arr[] = e.getCustomName().split(" ");
        if (arr.length>2)
            return;
        d = arr[0];
        if (d.equalsIgnoreCase("dragon")){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityDamageByDragon(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        if (damager.getCustomName()==null)
            return;
        String p;
        String d;
        String arr[] = damager.getCustomName().split(" ");
        if (arr.length>2)
            return;
        d = arr[0];
        p = arr[1];
        if (d.equalsIgnoreCase("dragon")){
            if (!(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
                return;
            }
            Player player = (Player) event.getEntity();
            if (!plugin.getHeroesManager().isSet(player)){
                event.setCancelled(true);
                return;
            }
            Player owner = plugin.getServer().getPlayer(p);
            if (plugin.getHeroesManager().getPlayerHero(owner) != HeroTypes.SAMURAI) {
                event.setCancelled(true);
                return;
            }
            int buff = 0;
            if (abilities.getSamurai().getBuffed().containsKey(owner))
                buff = abilities.getSamurai().getBuffed().get(owner);
            abilities.getSamurai().getBuffed().put(owner, ++buff);
            Bukkit.getServer().broadcastMessage(owner.getName() + ":" + buff);
            event.setDamage(4f);
        }
    }
    @EventHandler
    public void onPhaseChange(EnderDragonChangePhaseEvent event){
        Entity e = event.getEntity();
        if (e.getCustomName()==null)
            return;
        String d;
        String arr[] = e.getCustomName().split(" ");
        if (arr.length>2)
            return;
        d = arr[0];
        if (d.equalsIgnoreCase("dragon")) {
            if (!event.getNewPhase().equals(EnderDragon.Phase.CIRCLING)){
                event.setCancelled(true);
            }
        }
    }
}
