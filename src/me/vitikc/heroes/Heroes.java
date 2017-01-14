package me.vitikc.heroes;

import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.listeners.HeroesDamageListener;
import me.vitikc.heroes.listeners.HeroesItemListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class Heroes extends JavaPlugin {
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;

    @Override
    public void onEnable() {
        heroesManager = new HeroesManager();
        abilityUtils = new HeroesAbilityUtils();
        abilitiesManager = new HeroesAbilitiesManager(this);
        getServer().getPluginManager().registerEvents(new HeroesItemListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesDamageListener(this),this);
        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled.");
    }

    public HeroesManager getHeroesManager() {
        return heroesManager;
    }

    public HeroesAbilityUtils getAbilityUtils() {
        return abilityUtils;
    }

    public HeroesAbilitiesManager getAbilitiesManager() {
        return abilitiesManager;
    }
}
