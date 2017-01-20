package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesLegionCommanderAbilities {
    private HeroesMain plugin;
    private static Hashtable<Player, Player> duels;
    private static HashMap<Player, Float> damageEarned;

    public HeroesLegionCommanderAbilities(HeroesMain plugin){
        this.plugin = plugin;
        damageEarned = new HashMap<>();
        duels = new Hashtable<>();
    }

    public void Attack(Player player){
        for (int i = 0; i< 20; i++)
            plugin.getAbilityUtils().spawnArrow(player);
    }
    public void addDamage(Player player){
        if (damageEarned.get(player)==null){
            damageEarned.put(player,0.5f);
        }
        if (damageEarned.get(player)>5f){
            player.sendMessage("Maximum damage");
            return;
        }
        damageEarned.put(player,damageEarned.get(player) + 0.5f);
    }
    public void setDamage(Player player, float damage){
        if (damage > 5f) damage = 5f;
        damageEarned.put(player, damage);
    }
    public float getDamage(Player player){
        return damageEarned.get(player) != null ? damageEarned.get(player) : 0;
    }
    public void Defense(Player player, Player target, double damage){
        double vampire = 0.5;
        double counterAttackScale = 0.4;
        player.sendMessage("Counter!");
        target.sendMessage("Attacked by enemy defense ability");
        if (player.getHealth()<20f&&player.getHealth()>0f)
            player.setHealth(player.getHealth()+damage*vampire);
        target.damage(damage*counterAttackScale);
    }
    public void Ultimate(final Player player,final Player target){
        duels.put(player, target);
        player.sendMessage("DUEL STARTED");
        target.sendMessage("DUEL STARTED");
        new BukkitRunnable(){
            @Override
            public void run(){
                duels.remove(player);
                duels.remove(target);
                player.sendMessage("DUEL ENDED");
                target.sendMessage("DUEL ENDED");
            }
        }.runTaskLater(this.plugin, 40L);
    }
    public boolean isEarnedDamage(Player player){
        if (damageEarned.containsKey(player)){
            return true;
        }
        return false;
    }
    public boolean isPlayerInDuel(Player player){
        if (duels.containsKey(player)||duels.containsValue(player)){
            return true;
        }
        return false;
    }

    public static Hashtable<Player, Player> getDuels() {
        return duels;
    }
}
