package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.entity.HeroesEntityFollow;
import me.vitikc.heroes.entity.HeroesHealingWard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by Vitikc on 22/Jan/17.
 */
public class HeroesYurneroAbilities {
    private HeroesMain plugin;
    private HeroesConfigManager config;

    private final int SECONDS = 1000;

    private enum iValues{
        ATTACKRANGE(5),
        ATTACKDURATION(5),
        DEFENSEDURATION(5),
        ULTIMATEATTACKS(9),
        ULTIMATERADIUS(5);

        private int value;
        iValues(int value){
            this.value = value;
        }
    }
    private enum dValues{
        ATTACKDAMAGE(0.5),
        ATTACKTICK(0.5),
        ULTIMATETICK(0.5),
        ULTIMATEDAMAGE(1);

        private double value;
        dValues(double value){
            this.value = value;
        }
    }

    public HeroesYurneroAbilities(HeroesMain plugin){
        this.plugin = plugin;
        config = plugin.getConfigManager();

        loadFromConfig();
        setDefaultConfig();
    }

    public void Attack(final Player player){
        final int id = new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity e : player.getNearbyEntities(
                        iValues.ATTACKRANGE.value,
                        iValues.ATTACKRANGE.value,
                        iValues.ATTACKRANGE.value)
                        )
                {
                    if (!(e instanceof Player)) continue;
                    Player p = (Player) e;
                    p.damage(dValues.ATTACKDAMAGE.value);
                    p.sendMessage("You damaged by Yurnero attack ability");
                }
            }

        }.runTaskTimer(plugin,
                0L,
                (int)(20L * dValues.ATTACKTICK.value)).getTaskId();
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.getServer().getScheduler().cancelTask(id);
            }
        }.runTaskLater(plugin,20l * iValues.ATTACKDURATION.value);
    }
    public void Passive(Player player, Player target, double damage){
        player.sendMessage("Critical hit!");
        target.damage(damage); //Target is already damaged, so we just damage it by same damage to get x2 damage
    }
    public void Defense(final Player player){
        final Location loc = player.getLocation();
        final HeroesHealingWard healingWard = new HeroesHealingWard(((CraftWorld)player.getWorld()).getHandle(), player.getUniqueId());
        healingWard.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(healingWard, CreatureSpawnEvent.SpawnReason.CUSTOM);
        final  int id = new BukkitRunnable(){
            @Override
            public void run() {

            }
        }.runTaskTimer(plugin,
                0L,
                20L*iValues.DEFENSEDURATION.value).getTaskId();
        new BukkitRunnable(){
            @Override
            public void run() {
                ((CraftWorld)loc.getWorld()).getHandle().removeEntity(healingWard);
            }
        }.runTaskLater(plugin, 20L*iValues.DEFENSEDURATION.value);
    }
    public void Ultimate(final Player player, Player target){
        final Location savedLoc = player.getLocation();
        int attacks = iValues.ULTIMATEATTACKS.value;
        player.teleport(target.getLocation());
        target.damage(dValues.ULTIMATEDAMAGE.value);
        target.sendMessage("You damaged by Yurnero ultimate ability");
        attacks--;
        final int id = new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity e : player.getNearbyEntities(
                        iValues.ATTACKRANGE.value,
                        iValues.ATTACKRANGE.value,
                        iValues.ATTACKRANGE.value)
                        )
                {
                    if (!(e instanceof Player)) continue;
                    Player p = (Player) e;
                    Location l = e.getLocation();
                    //SOME STRANGE RANDOM
                    Random random = new Random();
                    int a = random.nextInt(2);
                    int b = random.nextInt(2);
                    boolean c = random.nextBoolean();
                    boolean d = random.nextBoolean();
                    if (c)
                        a = -a;
                    if (d)
                        b = -b;
                    //END OF STRANGE RANDOM
                    l.add(a,0,b);
                    player.teleport(l);
                    p.damage(dValues.ULTIMATEDAMAGE.value);
                    p.sendMessage("You damaged by Yurnero ultimate ability");
                }
            }
        }.runTaskTimer(plugin,
                (int)(20L * dValues.ULTIMATETICK.value),
                (int)(20L * dValues.ULTIMATETICK.value)).getTaskId();
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.getServer().getScheduler().cancelTask(id);
                player.teleport(savedLoc);
            }
        }.runTaskLater(plugin,
                (int)(20L * attacks * dValues.ATTACKTICK.value));
    }
    public void loadFromConfig(){
        String yu = "Yurnero";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(yu + "." + name))
                dValues.values()[i].value = config.getDouble(yu, name);
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(yu + "." + name))
                iValues.values()[i].value = config.getInt(yu, name);
        }
    }
    public void setDefaultConfig(){
        String yu = "Yurnero";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(yu + "." + name)){
                config.setDouble(yu, name, dValues.values()[i].value);
            }
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(yu + "." + name)){
                config.setInt(yu, name, iValues.values()[i].value);
            }
        }
        config.saveConfig();
    }
}
