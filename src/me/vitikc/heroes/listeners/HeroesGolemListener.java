package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.entity.HeroesGolem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * Created by Vitikc on 07/Feb/17.
 */
public class HeroesGolemListener implements Listener{
    private HeroesMain plugin;
    private HeroesAbilitiesManager abilities;

    public HeroesGolemListener(HeroesMain plugin){
        this.plugin = plugin;
        this.abilities = plugin.getAbilitiesManager();
    }
    @EventHandler
    public void onDragonFormRegen(EntityRegainHealthEvent event){
        Entity e = event.getEntity();
        if (!(e instanceof  Player)) return;
        Player p = (Player) e;
        if (!abilities.getDragonKnight().getGolemForm().containsKey(p)) return;
        HeroesGolem  g = abilities.getDragonKnight().getGolemForm().get(p);
        g.setHealth((float)(event.getAmount()*2 + g.getHealth()));
    }
    
    @EventHandler
    public void onDragonFormTakeDamage(EntityDamageByEntityEvent event){
        Entity e = event.getEntity();
        if (e.getCustomName()==null) return;
        String args[] = e.getCustomName().split(" ");
        if (args.length<2) return;
        String g = args[0];
        String o = args[1];
        if (!g.equalsIgnoreCase("golem")) return;
        Player owner = Bukkit.getPlayer(o);
        if (owner == null) return;
        if (abilities.getDragonKnight().getGolemForm().containsKey(owner)){
            owner.setInvulnerable(false);
            owner.damage(event.getDamage()/2);
            owner.setInvulnerable(true);
        }
    }
    @EventHandler
    public void onDeathInDragonForm(EntityDeathEvent event){
        Entity e = event.getEntity();
        if (e.getCustomName()==null) return;
        String args[] = e.getCustomName().split(" ");
        if (args.length<2) return;
        String g = args[0];
        String o = args[1];
        if (!g.equalsIgnoreCase("golem")) return;
        Player owner = Bukkit.getPlayer(o);
        if (owner == null) return;
        if (abilities.getDragonKnight().getGolemForm().containsKey(owner)){
            owner.setInvulnerable(false);
            owner.damage(owner.getHealth()*2);
            abilities.getDragonKnight().getGolemForm().remove(owner);
        }
        event.getDrops().clear();
        event.setDroppedExp(0);
    }


    @EventHandler
    public void onDragonKnightDismount(EntityDismountEvent event){
        Entity e = event.getDismounted();
        if (e.getCustomName()==null)
            return;
        String p = "0";
        HeroesGolem golem = null;
        for (HeroesGolem g : abilities.getDragonKnight().getGolemForm().values()){
            if (!e.getCustomName().equals(g.getCustomName()))
                continue;
            String arr[] = g.getCustomName().split(" ");
            p = arr[1];
            golem = g;
        }
        if (p.equals("0")||golem==null) return;
        final HeroesGolem ride = golem;
        final Player player = plugin.getServer().getPlayer(p);
        new BukkitRunnable(){
            @Override
            public void run() {
                ride.getBukkitEntity().setPassenger(player);
            }
        }.runTaskLater(plugin,1L);
    }
}
