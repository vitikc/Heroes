package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesLegionCommanderAbilities {
    private HeroesMain plugin;
    private HeroesConfigManager config;

    private double damagePerDuel = 0.5f;
    private double vampireScale = 0.3d;
    private double counterAttackScale = 0.3d;
    private double maximumDuelDamage = 5f;

    private int maximumArrows = 20;
    private int duelTime = 5;

    private static Hashtable<Player, Player> duels;
    private static HashMap<Player, Double> damageEarned;

    public HeroesLegionCommanderAbilities(HeroesMain plugin){
        this.plugin = plugin;
        config = plugin.getConfigManager();
        damageEarned = new HashMap<>();
        duels = new Hashtable<>();
        setDefaultConfig();
        loadFromConfig();
    }

    public void loadFromConfig(){
        String lc = "LegionCommander";
        if (config.getConfig().isSet(lc)){
            damagePerDuel = config.getDouble(lc, "damageperduel");
            vampireScale = config.getDouble(lc, "vampirescale");
            counterAttackScale = config.getDouble(lc, "counterattackscale");
            maximumDuelDamage = config.getDouble(lc, "maximumdueldamage");
            maximumArrows = config.getInt(lc, "maximumarrows");
            duelTime = config.getInt(lc, "dueltime");
        }
    }
    public void setDefaultConfig(){
        String lc = "LegionCommander";
        if (config.getConfig().isSet(lc)){
            return;
        }
        config.setDouble(lc, "damageperduel", damagePerDuel);
        config.setDouble(lc, "vampirescale", vampireScale);
        config.setDouble(lc, "counterattackscale", counterAttackScale);
        config.setDouble(lc, "maximumdueldamage", maximumDuelDamage);
        config.setInt(lc, "maximumarrows", maximumArrows);
        config.setInt(lc, "dueltime", duelTime);
        config.saveConfig();
    }

    public void addDamage(Player player){
        if (damageEarned.get(player)==null){
            damageEarned.put(player,damagePerDuel);
        }
        if (damageEarned.get(player)>=maximumDuelDamage){
            player.sendMessage("Maximum damage");
            return;
        }
        damageEarned.put(player,damageEarned.get(player) + damagePerDuel);
    }
    public void setDamage(Player player, double damage){
        if (damage >= maximumDuelDamage) damage = maximumDuelDamage;
        damageEarned.put(player, damage);
    }
    public double getDamage(Player player){
        return damageEarned.get(player) != null ? damageEarned.get(player) : 0;
    }
    public void Attack(Player player){
        for (int i = 0; i< maximumArrows; i++)
            plugin.getAbilityUtils().spawnArrow(player);
    }

    public void Defense(Player player, Player target, double damage){
        player.sendMessage("Counter!");
        target.sendMessage("Attacked by enemy defense ability");
        if (player.getHealth()<20f&&player.getHealth()>0f)
            player.setHealth(player.getHealth()+damage*vampireScale);
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
        }.runTaskLater(this.plugin, 20L * duelTime);
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
