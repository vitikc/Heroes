package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesBarbarianAbilities {
    private HeroesMain plugin;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;
    private HeroesConfigManager config;

    private int speedDuration = 5;
    private int poisonDuration = 5;
    private int slowDuration = 5;
    private int defenseRange = 3;

    private double defenseDamage = 2;
    private double ultimateThreshold = 6;
    private double ultimateMinDamage = 2;
    private double ultimateMaxDamage = 20;

    public HeroesBarbarianAbilities(HeroesMain plugin){
        this.plugin = plugin;
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
        config = plugin.getConfigManager();
        setDefaultConfig();
        loadFromConfig();
    }

    public void Attack(Player player, Player target) {
        plugin.getAbilityUtils().setSpeed(player, speedDuration);
        plugin.getAbilityUtils().setPoison(target, poisonDuration);
        plugin.getAbilityUtils().setSlow(target, slowDuration);
    }

    public void Defense(Player player){
        for (Entity e : player.getNearbyEntities(defenseRange,defenseRange,defenseRange)){
            if (!(e instanceof Player)) continue;
            Player p = (Player) e;
            p.damage(defenseDamage);
            p.sendMessage("You damaged by defense ability");
        }
    }
    public void Ultimate(Player player, Player target){
        if (target.getHealth() <= ultimateThreshold){
            target.damage(ultimateMaxDamage);

            plugin.getServer().broadcastMessage(target.getDisplayName() +
                    " killed by " +
                    player.getDisplayName() +
                    "'s ultimate");
            return;
        } else {
            cooldown.setCooldown(player,
                    cooldownValues.barbarianUltimate,
                    cooldownValues.getValues().get(cooldownValues.barbarianUltimate));
            target.damage(ultimateMinDamage);
        }
    }
    public void loadFromConfig(){
        String ba = "Barbarian";
        if (config.getConfig().isSet(ba)){
            speedDuration = config.getInt(ba, "speedduration");
            poisonDuration = config.getInt(ba, "poisonduration");
            slowDuration = config.getInt(ba, "slowduration");
            defenseRange = config.getInt(ba, "defenserange");
            defenseDamage = config.getDouble(ba, "defensedamage");
            ultimateThreshold = config.getDouble(ba, "ultimatethreshold");
            ultimateMinDamage = config.getDouble(ba, "ultimateMinDamage");
            ultimateMaxDamage = config.getDouble(ba, "ultimateMaxDamage");
        }
    }
    public void setDefaultConfig(){
        String ba = "Barbarian";
        if (config.getConfig().isSet(ba)){
            return;
        }
        config.setInt(ba, "speedduration",speedDuration);
        config.setInt(ba, "poisonduration",poisonDuration);
        config.setInt(ba, "slowduration",slowDuration);
        config.setInt(ba, "defenserange",defenseRange);
        config.setDouble(ba,"defensedamage",defenseDamage);
        config.setDouble(ba,"ultimatethreshold",ultimateThreshold);
        config.setDouble(ba,"ultimateMinDamage",ultimateMinDamage);
        config.setDouble(ba,"ultimateMaxDamage",ultimateMaxDamage);
        config.saveConfig();
    }
}
