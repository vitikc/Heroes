package me.vitikc.heroes;

import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.commands.HeroesCommandExecutor;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import me.vitikc.heroes.entity.HeroesCustomEntities;
import me.vitikc.heroes.listeners.HeroesDamageListener;
import me.vitikc.heroes.listeners.HeroesDeathListener;
import me.vitikc.heroes.listeners.HeroesItemListener;
import me.vitikc.heroes.listeners.HeroesPlayerMoveListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesMain extends JavaPlugin {
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;
    private HeroesConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new HeroesConfigManager(this);
        heroesManager = new HeroesManager(this);
        cooldown = new HeroesCooldown();
        abilityUtils = new HeroesAbilityUtils();
        abilitiesManager = new HeroesAbilitiesManager(this);
        cooldownValues = new HeroesCooldownValues(this);

        getServer().getPluginManager().registerEvents(new HeroesItemListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesDamageListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesPlayerMoveListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesDeathListener(this),this);

        getCommand("heroes").setExecutor(new HeroesCommandExecutor(this));

        HeroesCustomEntities.registerEntities();
        getLogger().info("Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        HeroesCustomEntities.unregisterEntities();
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

    public HeroesCooldown getCooldown(){
        return cooldown;
    }

    public HeroesCooldownValues getCooldownValues(){
        return cooldownValues;
    }

    public HeroesConfigManager getConfigManager(){
        return configManager;
    }
}
