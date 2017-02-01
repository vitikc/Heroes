package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Vitikc on 31/Jan/17.
 */
public class HeroesSamuraiAbilities {
    private HeroesMain plugin;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;
    private HeroesConfigManager config;
    private HeroesAbilityUtils utils;

    public HeroesSamuraiAbilities(HeroesMain plugin){
        this.plugin = plugin;
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
        config = plugin.getConfigManager();
        utils = plugin.getAbilityUtils();
    }

    public void Attack(Player player){
        Vector vector = player.getLocation().getDirection().multiply(4);
        vector.setY(0);
        player.setVelocity(vector);

        for(Entity e : utils.getEntitiesAtLine(player, 10))
            player.sendMessage(e.getType().name());
    }

    public void Defense(){

    }

    public void Ultimate(){

    }
}
