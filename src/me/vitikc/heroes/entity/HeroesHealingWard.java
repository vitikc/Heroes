package me.vitikc.heroes.entity;

import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Player;


import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/**
 * Created by Vitikc on 29/Jan/17.
 * TY to @Arektor NMS Tutorials
 */
public class HeroesHealingWard extends EntityChicken {
    private UUID owner;

    public HeroesHealingWard(World world, UUID owner) {
        super(world);
        this.setCustomNameVisible(true);
        this.setCustomName("Healing ward");
        this.owner = owner;

        this.setHealth(1f);
        this.expToDrop = 0;

        LinkedHashSet goalB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new HeroesEntityFollow(this, 2f));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
    }
    public HeroesHealingWard(Location loc) {
        super(((CraftWorld)loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object)
    {
        Field field;
        Object o = null;
        try
        {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return o;
    }
    public Player getPlayerOwner(){
        return Bukkit.getPlayer(this.owner);
    }

}
