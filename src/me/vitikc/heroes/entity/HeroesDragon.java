package me.vitikc.heroes.entity;

import me.vitikc.heroes.HeroesMain;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.UUID;

import static me.vitikc.heroes.entity.HeroesNMSUtils.getPrivateField;

/**
 * Created by Vitikc on 01/Feb/17.
 */
public class HeroesDragon extends EntityEnderDragon {
    private UUID owner;
    private HeroesMain plugin;

    public HeroesDragon(World world, UUID owner, HeroesMain plugin) {
        super(world);
        this.setCustomNameVisible(true);
        this.setCustomName("Dragon");
        this.owner = owner;
        this.plugin = plugin;

        this.setHealth(1f);
        this.setInvulnerable(true);
        this.expToDrop = 0;
        this.setAI(false);

        LinkedHashSet goalB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        LinkedHashSet goalC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        LinkedHashSet targetB = (LinkedHashSet)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        LinkedHashSet targetC = (LinkedHashSet)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();


        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);

        dieAfter();
    }

    private void dieAfter(){
        new BukkitRunnable(){
            @Override
            public void run() {
                die();
            }
        }.runTaskLater(plugin, 10L);
    }
    public Player getPlayerOwner(){
        return Bukkit.getPlayer(this.owner);
    }
}
