package me.vitikc.heroes.abilities;

import me.vitikc.heroes.Heroes;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 12/Jan/17.
 */
public class HeroesAbilitiesManager {
    private Heroes plugin;
    private HeroesAbilityUtils abiliyUtils;
    public HeroesAbilitiesManager(Heroes plugin) {
        this.plugin = plugin;
    }

    //BARBARIAN
    public void BarbarianAttackAbility(Player player, Player target){
      /*  abiliyUtils.setSpeed(player, 5);
        abiliyUtils.setPoison(target, 5);
        abiliyUtils.setSlow(target, 5);*/
    }
    public void BarbarianDefenseAbility(Player player){
        int range = 3;
        int damage = 2;
        for (Entity e : player.getNearbyEntities(range,range,range)){
            if (!(e instanceof Player)) return;
            Player p = (Player) e;
            p.damage(damage);
            p.sendMessage("You damaged by defense ability");
        }
    }
    public void BarbarianUltimateAbility(Player player, Player target){
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
    //ENDBARBARIAN
    //LEGIONCOMMANDER
    //ENDLEGIONCOMMANDER
}
