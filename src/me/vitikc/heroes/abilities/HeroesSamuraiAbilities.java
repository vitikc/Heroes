package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import me.vitikc.heroes.entity.HeroesDragon;
import net.minecraft.server.v1_11_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Vitikc on 31/Jan/17.
 */
public class HeroesSamuraiAbilities {
    private HeroesMain plugin;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;
    private HeroesConfigManager config;
    private HeroesAbilityUtils utils;

    private static HashMap<Player, Integer> buffed = new HashMap<>();

    private HashSet<PotionEffectType> effects = new HashSet<>();

    public HeroesSamuraiAbilities(HeroesMain plugin){
        this.plugin = plugin;
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
        config = plugin.getConfigManager();
        utils = plugin.getAbilityUtils();
        effects.add(PotionEffectType.SPEED);
        effects.add(PotionEffectType.INCREASE_DAMAGE);
    }

    public void Attack(Player player){
        Vector vector = player.getLocation().getDirection().multiply(4);
        vector.setY(0);
        player.setVelocity(vector);

        for(Entity e : utils.getEntitiesAtLine(player, 10)) {
            player.sendMessage(e.getType().name());
            //e damage
            //if (e.isDead()) no cooldown
        }
    }

    public void Defense(){

    }

    public void Ultimate(Player player){
        HeroesDragon dragon = new HeroesDragon(((CraftWorld)player.getWorld()).getHandle(),player.getUniqueId(),plugin);
        dragon.setCustomName(dragon.getCustomName() + " " + player.getDisplayName());
        Location loc = player.getLocation();
        dragon.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw()-180, loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(dragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Vector vector = new Vector (loc.getDirection().getX()*5, 0, loc.getDirection().getZ()*5);
        dragon.getBukkitEntity().setVelocity(vector);
        buff(player);
    }

    public HashMap<Player,Integer> getBuffed(){
        return buffed;
    }

    private void buff(final Player player){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(PotionEffectType pt : effects){
                    int ampf = 0;
                    if (buffed.containsKey(player)){
                        ampf = buffed.get(player);
                        if (ampf>3) ampf = 3;
                    }
                    PotionEffect pe = new PotionEffect(pt,100,ampf);
                    player.addPotionEffect(pe);
                }
                buffed.put(player, 0);
            }
        }.runTaskLater(plugin, 10L);
    }
}
