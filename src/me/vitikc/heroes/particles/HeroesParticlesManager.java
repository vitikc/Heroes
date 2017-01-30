package me.vitikc.heroes.particles;

import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 30/Jan/17.
 */
public class HeroesParticlesManager {

    public void circle(Location l,Particle p, int r){
        for (double i = 0; i < 50; i+=0.5){
            double x = r*Math.cos(i);
            double z = r*Math.sin(i);
            l.add(x,0,z);
            l.getWorld().spawnParticle(p,l, 0);
            l.subtract(x,0,z);
        }
    }
}
