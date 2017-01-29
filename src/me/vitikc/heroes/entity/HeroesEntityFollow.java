package me.vitikc.heroes.entity;

import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;



public class HeroesEntityFollow extends PathfinderGoal {

    private double speed;

    private EntityInsentient entity;

    private Location loc;

    private NavigationAbstract navigation;

    public HeroesEntityFollow(EntityInsentient entity, Location loc, double speed)
    {
        this.entity = entity;
        this.loc = loc;
        this.navigation = this.entity.getNavigation();
        this.speed = speed;
    }

    public boolean a() {
        return true;
    }

    public void c()
    {
        PathEntity pathEntity = this.navigation.a(loc.getX(), loc.getY(), loc.getZ());

        this.navigation.a(pathEntity, speed);
    }
}