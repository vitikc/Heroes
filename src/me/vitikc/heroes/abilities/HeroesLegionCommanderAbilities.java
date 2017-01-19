package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesLegionCommanderAbilities {
    private HeroesMain plugin;
    private static HashSet<Player> duels;



    public HeroesLegionCommanderAbilities(HeroesMain plugin){
        this.plugin = plugin;
        duels = new HashSet<>();
    }

    public void Attack(Player player){
        for (int i = 0; i< 20; i++)
            plugin.getAbilityUtils().spawnArrow(player);
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
        duels.add(player);
        duels.add(target);
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
    public boolean isPlayerInDuel(Player player){
        if (duels.contains(player)){
            return true;
        }
        return false;
    }
}
