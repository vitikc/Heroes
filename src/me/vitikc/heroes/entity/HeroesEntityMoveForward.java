package me.vitikc.heroes.entity;

import me.vitikc.heroes.HeroesMain;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Vitikc on 01/Feb/17.
 */
public class HeroesEntityMoveForward extends PathfinderGoal{
    private HeroesMain plugin;
    private EntityInsentient entity;
    private NavigationAbstract navigation;
    private Player owner;

    public HeroesEntityMoveForward(EntityInsentient entity, HeroesMain plugin)
    {
        this.entity = entity;
        this.navigation = this.entity.getNavigation();
        this.plugin = plugin;
        this.owner = ((HeroesDragon)entity).getPlayerOwner();
    }

    public boolean a() {
        if(entity.getGoalTarget() != null){
            return false;
        }
        return true;
    }

    public void c()
    {
        moveForward();
        dieAfter();
    }
    private void moveForward(){
        new BukkitRunnable(){
            @Override
            public void run() {
                Location l = owner.getLocation();
                Vector v = l.getDirection().normalize();
                l.add(v.getX()*10,0,v.getZ()*10);
                PathEntity pathEntity = navigation.a(l.getX(),l.getY(),l.getZ());
                navigation.a(pathEntity, 4);

            }
        }.runTaskLater(plugin,5L);

    }
    private void dieAfter(){
        new BukkitRunnable(){
            @Override
            public void run() {
                entity.die();
            }
        }.runTaskLater(plugin, 200L);
    }
}
