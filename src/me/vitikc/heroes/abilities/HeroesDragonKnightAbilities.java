package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.entity.HeroesGolem;
import me.vitikc.heroes.particles.HeroesParticlesManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Vitikc on 31/Jan/17.
 */
public class HeroesDragonKnightAbilities {
    private HeroesMain plugin;
    private HeroesAbilityUtils utils;
    private HeroesConfigManager config;
    private HeroesParticlesManager particles;

    private static HashSet<Player> stunned = new HashSet<>();
    private static HashMap<Player, HeroesGolem> golemForm = new HashMap<>();

    private enum dValues{
        ATTACKDAMAGE(1f);

        private double value;
        dValues(double value){
            this.value = value;
        }
    }
    private enum iValues{
        STUNDURATION(2);

        private int value;
        iValues(int value){
            this.value = value;
        }
    }

    public HeroesDragonKnightAbilities(HeroesMain plugin){
        this.plugin = plugin;
        utils = plugin.getAbilityUtils();
        config = plugin.getConfigManager();
        particles = plugin.getParticlesManager();

        loadFromConfig();
        setDefaultConfig();
    }

    public void Attack(Player player){
        for (Entity e : utils.getEntitiesAtLine(player, 7)){
            if (!(e instanceof Player))
                continue;
            Player p = (Player) e;
            p.damage(dValues.ATTACKDAMAGE.value);
        }
        particles.breath(player.getEyeLocation(), Particle.FLAME,7);
    }

    public void Stun(final Player player){
        stunned.add(player);
        new BukkitRunnable(){
            @Override
            public void run() {
                stunned.remove(player);
            }
        }.runTaskLater(plugin, 20L*iValues.STUNDURATION.value);
    }

    public void Ultimate(final Player player){
        final HeroesGolem golem = new HeroesGolem(((CraftWorld)player.getWorld()).getHandle(),player.getUniqueId(),plugin);
        golem.setCustomName(golem.getCustomName() + " " + player.getDisplayName());
        Location loc = player.getLocation();
        golem.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(golem, CreatureSpawnEvent.SpawnReason.CUSTOM);
        golem.setHealth((float)player.getHealth()*2);
        golem.getBukkitEntity().setPassenger(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,20*60, 200));
        player.setInvulnerable(true);
        setInvisible(player);
        golemForm.put(player, golem);
        player.setAllowFlight(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                golemForm.remove(player);
                golem.getBukkitEntity().remove();
                setVisible(player);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.setInvulnerable(false);
                player.setAllowFlight(false);
            }
        }.runTaskLater(plugin, 20L * 60);
    }

    private void setInvisible(Player p){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.hidePlayer(p);
        }
    }
    private void setVisible(Player p){
        for (Player player : Bukkit.getOnlinePlayers()){
            player.showPlayer(p);
        }
    }

    public static HashSet<Player> getStunned() {
        return stunned;
    }

    public static HashMap<Player, HeroesGolem> getGolemForm() {
        return golemForm;
    }

    public void loadFromConfig(){
        String dk = "DragonKnight";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(dk + "." + name))
                dValues.values()[i].value = config.getDouble(dk, name);
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(dk + "." + name))
                iValues.values()[i].value = config.getInt(dk, name);
        }
    }
    public void setDefaultConfig(){
        String dk = "DragonKnight";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(dk + "." + name)){
                config.setDouble(dk, name, dValues.values()[i].value);
            }
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(dk + "." + name)){
                config.setInt(dk, name, iValues.values()[i].value);
            }
        }
        config.saveConfig();
    }
}
