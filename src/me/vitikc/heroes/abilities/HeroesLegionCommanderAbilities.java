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

    private enum dValues{
        DAMAGEPERDUEL(0.5),
        VAMPIRESCALE(0.3),
        COUNTERATTACKSCALE(0.3),
        MAXDUELDAMAGE(5.0);

        private double value;
        dValues(double value){
            this.value = value;
        }
    }
    private enum iValues{
        MAXARROWS(20),
        DUELTIME(5);

        private int value;
        iValues(int value){
            this.value = value;
        }
    }

    private static Hashtable<Player, Player> duels;
    private static HashMap<Player, Double> damageEarned;

    public HeroesLegionCommanderAbilities(HeroesMain plugin){
        this.plugin = plugin;
        config = plugin.getConfigManager();
        damageEarned = new HashMap<>();
        duels = new Hashtable<>();

        loadFromConfig();
        setDefaultConfig();
    }

    public void addDamage(Player player){
        if (damageEarned.get(player)==null){
            damageEarned.put(player,dValues.DAMAGEPERDUEL.value);
        }
        if (damageEarned.get(player)>=dValues.MAXDUELDAMAGE.value){
            player.sendMessage("Maximum damage");
            return;
        }
        damageEarned.put(player,damageEarned.get(player) + dValues.DAMAGEPERDUEL.value);
    }
    public void setDamage(Player player, double damage){
        if (damage >= dValues.MAXDUELDAMAGE.value) damage = dValues.MAXDUELDAMAGE.value;
        damageEarned.put(player, damage);
    }
    public double getDamage(Player player){
        return damageEarned.get(player) != null ? damageEarned.get(player) : 0;
    }
    public void Attack(Player player){
        for (int i = 0; i< iValues.MAXARROWS.value; i++)
            plugin.getAbilityUtils().spawnArrow(player);
    }

    public void Defense(Player player, Player target, double damage){
        player.sendMessage("Counter!");
        target.sendMessage("Attacked by enemy defense ability");
        if (player.getHealth()<20f&&player.getHealth()>0f)
            player.setHealth(player.getHealth()+damage*dValues.VAMPIRESCALE.value);
        target.damage(damage*dValues.COUNTERATTACKSCALE.value);
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
        }.runTaskLater(this.plugin, 20L * iValues.DUELTIME.value);
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
    public void loadFromConfig(){
        String lc = "LegionCommander";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(lc + "." + name))
                dValues.values()[i].value = config.getDouble(lc, name);
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(lc + "." + name))
                iValues.values()[i].value = config.getInt(lc, name);
        }
    }
    public void setDefaultConfig(){
        String lc = "LegionCommander";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(lc + "." + name)){
                config.setDouble(lc, name, dValues.values()[i].value);
            }
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(lc + "." + name)){
                config.setInt(lc, name, iValues.values()[i].value);
            }
        }
        config.saveConfig();
    }
}
