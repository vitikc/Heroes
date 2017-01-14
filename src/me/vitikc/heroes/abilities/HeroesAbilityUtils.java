package me.vitikc.heroes.abilities;

import me.vitikc.heroes.Heroes;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesAbilityUtils {
    private boolean isLookingAtPlayer(Player player, Player target)
    {
        Location eye = player.getEyeLocation();

        Vector v1 = target.getLocation().toVector().subtract(eye.toVector());
        Vector v2 = target.getEyeLocation().toVector().subtract(eye.toVector());
        Vector v3 = target.getEyeLocation().toVector().subtract(eye.toVector());
        Vector v4 = target.getEyeLocation().toVector().subtract(eye.toVector());

        v2.setY((v2.getY()+v1.getY())/1.5);
        v3.setY((v3.getY()+v1.getY())/3);

        double d1 = v1.normalize().dot(eye.getDirection());
        double d2 = v2.normalize().dot(eye.getDirection());
        double d3 = v3.normalize().dot(eye.getDirection());
        double d4 = v4.normalize().dot(eye.getDirection());

        boolean b1 = d1>0.99D;
        boolean b2 = d2>0.99D;
        boolean b3 = d3>0.99D;
        boolean b4 = d4>0.99D;

        return (b1||b2||b3||b4);
    }
    public void setSpeed(Player target, int duration){
        target.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,duration*20,1));
    }
    public void setSlow(Player target, int duration){
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,duration*20,1));
    }
    public void setPoison(Player target, int duration){
        target.addPotionEffect(new PotionEffect(PotionEffectType.POISON,duration*20,1));
    }
    public void setRegeneration(Player target, int duration){
        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,duration*20,1));
    }

    public Player getTargetPlayer(Player player){
        for (Entity e : player.getNearbyEntities(3,3,3)){
            if (!(e instanceof Player)) continue;
            if (isLookingAtPlayer(player,(Player)e)){
                return (Player) e;
            }
        }
        return null;
    }
    public boolean isCounterAttackProc(int chance){
        double r = Math.random();
        if (r*100 <= chance) return true;
        return false;
    }

}
