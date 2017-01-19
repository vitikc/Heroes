package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

/**
 * Created by Vitikc on 12/Jan/17.
 */
public class HeroesAbilitiesManager {
    private HeroesMain plugin;
    private HeroesBarbarianAbilities barbarian;
    private HeroesLegionCommanderAbilities legionCommander;
    public HeroesAbilitiesManager(HeroesMain plugin) {
        this.plugin = plugin;
        barbarian = new HeroesBarbarianAbilities(plugin);
        legionCommander = new HeroesLegionCommanderAbilities(plugin);
    }

    public HeroesBarbarianAbilities getBarbarian() {
        return barbarian;
    }

    public HeroesLegionCommanderAbilities getLegionCommander() {
        return legionCommander;
    }
}
