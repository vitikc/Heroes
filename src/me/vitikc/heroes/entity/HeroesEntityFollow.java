package me.vitikc.heroes.entity;

import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;



public class HeroesEntityFollow extends PathfinderGoal {

    private double speed;

    private HeroesHealingWard ward;


    private NavigationAbstract navigation;

    public HeroesEntityFollow(HeroesHealingWard ward, double speed)
    {
        this.ward = ward;
        this.navigation = this.ward.getNavigation();
        this.speed = speed;
    }

    public boolean a() {
        if(ward.getGoalTarget() != null){
            return false;
        }

        Location pLoc = ward.getPlayerOwner().getLocation();
        Location eLoc = ward.getBukkitEntity().getLocation();

        double distance = pLoc.distance(eLoc);
        if(distance < 5){
            return false;
        }

        return true;
    }

    public void c()
    {
        Location l = ward.getPlayerOwner().getLocation();
        PathEntity pathEntity = this.navigation.a(l.getX(),l.getY(), l.getZ());
        this.navigation.a(pathEntity, speed);
    }
}