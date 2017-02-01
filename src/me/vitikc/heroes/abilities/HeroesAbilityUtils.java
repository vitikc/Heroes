package me.vitikc.heroes.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

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
    private int getRandom(int max , int min) {
        int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
        return ii;
    }
    public void spawnArrow(Player owner){
        Location location = owner.getTargetBlock((Set<Material>) null,10).getLocation();
        location.add(getRandom(3,1),10,getRandom(3,1));
        Vector direction = new Vector(0,-1,0);
        Arrow arrow = owner.getWorld().spawnArrow(location,direction,5.0f,0f);
        arrow.setCustomName(owner.getDisplayName());
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
    public HashSet<Entity> getEntitiesAtLine(Player p, int range){
        List<Entity> entities = p.getNearbyEntities(range,range,range);
        HashSet<Entity> targets = new HashSet<>();
        ArrayList<Block> sightBlock = (ArrayList<Block>) p.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            targets.add(entities.get(k));
                        }
                    }
                }
            }
        }
        return targets; //Return null/nothing if no entity was found
    }


    public boolean isChanceProc(int chance){
        double r = Math.random();
        if (r*100 <= chance) return true;
        return false;
    }

}
