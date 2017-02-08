package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import me.vitikc.heroes.entity.HeroesDragon;
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
    private HeroesConfigManager config;
    private HeroesAbilityUtils utils;

    private static HashMap<Player, Integer> buffed = new HashMap<>();
    private static HashSet<Player> reflect = new HashSet<>();

    private HashSet<PotionEffectType> effects = new HashSet<>();

    private enum dValues{
        ATTACKDAMAGE(1f);

        private double value;
        dValues(double value){
            this.value = value;
        }
    }
    private enum iValues{
        BUFFDURATION(10),
        REFLECTDURATION(1);

        private int value;
        iValues(int value){
            this.value = value;
        }
    }

    public HeroesSamuraiAbilities(HeroesMain plugin){
        this.plugin = plugin;
        cooldown = plugin.getCooldown();
        config = plugin.getConfigManager();
        utils = plugin.getAbilityUtils();
        effects.add(PotionEffectType.SPEED);
        effects.add(PotionEffectType.INCREASE_DAMAGE);

        loadFromConfig();
        setDefaultConfig();
    }

    public void Attack(Player player){
        Vector vector = player.getLocation().getDirection().multiply(4);
        vector.setY(0);
        player.setVelocity(vector);
        boolean isGetsCooldown = true;
        for(Entity e : utils.getEntitiesAtLine(player, 10)) {
            if (!(e instanceof Player)) continue;
            Player p = (Player) e;
            p.damage(dValues.ATTACKDAMAGE.value);
            if (p.isDead()) isGetsCooldown = false;
        }
        if (isGetsCooldown)
            cooldown.putCooldown(player,
                    HeroesCooldownValues.Values.SAMURAIATTACK.name(),
                    HeroesCooldownValues.Values.SAMURAIATTACK.get());
    }

    public void Defense(final Player player){
        reflect.add(player);
        new BukkitRunnable(){
            @Override
            public void run() {
                reflect.remove(player);
            }
        }.runTaskLater(plugin, 20L*iValues.REFLECTDURATION.value);
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

    public static HashSet<Player> getReflected() {
        return reflect;
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
                    PotionEffect pe = new PotionEffect(pt,20*iValues.BUFFDURATION.value,ampf);
                    player.addPotionEffect(pe);
                }
                buffed.put(player, 0);
            }
        }.runTaskLater(plugin, 10L);
    }
    public void loadFromConfig(){
        String sa = "Samurai";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(sa + "." + name))
                dValues.values()[i].value = config.getDouble(sa, name);
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(sa + "." + name))
                iValues.values()[i].value = config.getInt(sa, name);
        }
    }
    public void setDefaultConfig(){
        String sa = "Samurai";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(sa + "." + name)){
                config.setDouble(sa, name, dValues.values()[i].value);
            }
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(sa + "." + name)){
                config.setInt(sa, name, iValues.values()[i].value);
            }
        }
        config.saveConfig();
    }
}
