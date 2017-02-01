package me.vitikc.heroes.items;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.particles.HeroesParticlesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Vitikc on 30/Jan/17.
 */
public class HeroesItemsManager {
    private HeroesMain plugin;
    private HeroesParticlesManager particles;

    private HashSet<Location> bases = new HashSet<>();

    public static HashMap<Player, Boolean> teleports = new HashMap<>();

    public HeroesItemsManager(HeroesMain plugin){
        this.plugin = plugin;
        particles = plugin.getParticlesManager();
    }

    private Location getNearbyBase(Location l){
        double d=0;
        double d1;
        if (bases.isEmpty())
            return null;
        for (Location location : bases){
            d1 = location.distance(l);
            if (d==0||d>d1) d=d1;
        }
        for (Location location: bases){
            if (d==location.distance(l))
                return location;
        }
        return null;
    }

    public void addBase(Location l){
        bases.add(l);
    }

    public void teleportItem(final Player player){
        teleports.put(player,true);
        final int id = new BukkitRunnable(){
            @Override
            public void run() {
                particles.spiral(player.getLocation(), Particle.FIREWORKS_SPARK, 1);
            }
        }.runTaskTimer(plugin,0L,10L).getTaskId();
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getServer().getScheduler().cancelTask(id);
                Location l = getNearbyBase(player.getLocation());
                if (l==null)
                    return;
                if (!teleports.get(player)){
                    return;
                }
                teleports.remove(player);
                player.teleport(l);
            }
        }.runTaskLater(plugin, 20L * 3);
    }
}
