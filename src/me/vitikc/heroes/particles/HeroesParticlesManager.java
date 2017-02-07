package me.vitikc.heroes.particles;

import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
    public void spiral(Location l, Particle p, int r){
        for (double i = 0; i < 50; i+=0.5){
            double x = r*Math.cos(i);
            double y = i*0.2;
            double z = r*Math.sin(i);
            l.add(x,y,z);
            l.getWorld().spawnParticle(p,l, 0);
            l.subtract(x,y,z);
        }
    }
    public void verticalCircle(Location l, Particle p, int r){
        for (double i = 0; i < 50; i+=0.5){
            double y = r*Math.cos(i);
            double z = r*Math.sin(i);
            l.add(0,y,z);
            l.getWorld().spawnParticle(p,l, 0);
            l.subtract(0,y,z);
        }
    }
    public void lemniscate(Location l, Particle p, double d){
        for (double i = 0; i < 50; i+=0.5){
            double x = (d*Math.cos(i))/(1+Math.pow(Math.sin(i),2));
            double z = (d*Math.sin(i)*Math.cos(i))/(1+Math.pow(Math.sin(i),2));
            l.add(x,0,z);
            l.getWorld().spawnParticle(p, l, 0);
            l.subtract(x,0,z);
        }
    }
    public void breath(Location l, Particle p, int r){
        Vector dir = l.getDirection();
        dir.normalize();
        for (double i = 0; i< r; i++) {
            l.add(dir).multiply(1);
            double o = i*0.02;
            l.getWorld().spawnParticle(p,l,0,o,o,o);
            l.getWorld().spawnParticle(p,l,0,-o,o,o);
            l.getWorld().spawnParticle(p,l,0,o,o,-o);
            l.getWorld().spawnParticle(p,l,0,-o,o,-o);
            l.getWorld().spawnParticle(p,l,0,-o,-o,o);
            l.getWorld().spawnParticle(p,l,0,o,-o,-o);
            l.getWorld().spawnParticle(p,l,0,-o,-o,-o);
            l.getWorld().spawnParticle(p,l,0,-o,0,o);
            l.getWorld().spawnParticle(p,l,0,o,0,-o);
            l.getWorld().spawnParticle(p,l,0,-o,0,-o);
            l.getWorld().spawnParticle(p,l,0,o,0,o);
            l.getWorld().spawnParticle(p,l,0,-o,0,0);
            l.getWorld().spawnParticle(p,l,0,o,0,0);
            l.getWorld().spawnParticle(p,l,0);
        }
    }
}
