package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesBarbarianAbilities {
    HeroesMain plugin;

    public HeroesBarbarianAbilities(HeroesMain plugin){
        this.plugin = plugin;
    }

    public void Attack(Player player, Player target) {
        plugin.getAbilityUtils().setSpeed(player, 5);
        plugin.getAbilityUtils().setPoison(target, 5);
        plugin.getAbilityUtils().setSlow(target, 5);
    }

    public void Defense(Player player){
        int range = 3;
        int damage = 2;
        for (Entity e : player.getNearbyEntities(range,range,range)){
            if (!(e instanceof Player)) return;
            Player p = (Player) e;
            p.damage(damage);
            p.sendMessage("You damaged by defense ability");
        }
    }
    public void Ultimate(Player player, Player target){
        if (target.getHealth() <=6 ){
            target.damage(20);

            plugin.getServer().broadcastMessage(target.getDisplayName() +
                    " killed by " +
                    player.getDisplayName() +
                    "'s ultimate");
            return;
        } else {
            // TODO: 11/Jan/17 Cooldown
            target.damage(2);
        }
    }
}
