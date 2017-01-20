package me.vitikc.heroes;

import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.commands.HeroesCommandExecutor;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
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

    @Override
    public void onEnable() {
        heroesManager = new HeroesManager();
        cooldown = new HeroesCooldown();
        cooldownValues = new HeroesCooldownValues();
        abilityUtils = new HeroesAbilityUtils();
        abilitiesManager = new HeroesAbilitiesManager(this);
        getServer().getPluginManager().registerEvents(new HeroesItemListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesDamageListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesPlayerMoveListener(this),this);
        getServer().getPluginManager().registerEvents(new HeroesDeathListener(this),this);
        getCommand("heroes").setExecutor(new HeroesCommandExecutor(this));
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

    public HeroesCooldown getCooldown(){
        return cooldown;
    }

    public HeroesCooldownValues getCooldownValues(){
        return cooldownValues;
    }
}
