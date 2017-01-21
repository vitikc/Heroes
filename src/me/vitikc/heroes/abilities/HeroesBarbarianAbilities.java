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

    private enum dValues{
        DEFENSEDAMAGE(2),
        ULTIMATETHRESHOLD(6),
        ULTIMATEMINDAMAGE(2),
        ULTIMATEMAXDAMAGE(20);

        private double value;
        dValues(double value){
            this.value = value;
        }
    }
    private enum iValues{
        SPEEDDURATION(5),
        SLOWDURATION(5),
        POISONDURATION(5),
        DEFENSERANGE(3);

        private int value;
        iValues(int value){
            this.value = value;
        }
    }

    public HeroesBarbarianAbilities(HeroesMain plugin){
        this.plugin = plugin;
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
        config = plugin.getConfigManager();
        loadFromConfig();
        setDefaultConfig();
    }

    public void Attack(Player player, Player target) {
        plugin.getAbilityUtils().setSpeed(player, iValues.SPEEDDURATION.value);
        plugin.getAbilityUtils().setPoison(target, iValues.POISONDURATION.value);
        plugin.getAbilityUtils().setSlow(target, iValues.SLOWDURATION.value);
    }

    public void Defense(Player player){
        for (Entity e : player.getNearbyEntities(
                iValues.DEFENSERANGE.value,
                iValues.DEFENSERANGE.value,
                iValues.DEFENSERANGE.value)
                ){
            if (!(e instanceof Player)) continue;
            Player p = (Player) e;
            p.damage(dValues.DEFENSEDAMAGE.value);
            p.sendMessage("You damaged by defense ability");
        }
    }
    public void Ultimate(Player player, Player target){
        if (target.getHealth() <= dValues.ULTIMATETHRESHOLD.value){
            target.damage(dValues.ULTIMATEMAXDAMAGE.value);

            plugin.getServer().broadcastMessage(target.getDisplayName() +
                    " killed by " +
                    player.getDisplayName() +
                    "'s ultimate");
            return;
        } else {
            cooldown.setCooldown(player,
                    HeroesCooldownValues.CooldownsValues.BARBARIANULTIMATE.toString(),
                    HeroesCooldownValues.CooldownsValues.BARBARIANULTIMATE.get());
            target.damage(dValues.ULTIMATEMINDAMAGE.value);
        }
    }
    public void loadFromConfig(){
        String ba = "Barbarian";
        for (int i = 0; i < dValues.values().length; i++){
            String name = dValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(ba + "." + name))
                dValues.values()[i].value = config.getDouble(ba, name);
        }
        for (int i = 0; i < iValues.values().length; i++){
            String name = iValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(ba + "." + name))
                iValues.values()[i].value = config.getInt(ba, name);
        }
    }
    public void setDefaultConfig() {
        String ba = "Barbarian";
        for (int i = 0; i < dValues.values().length; i++) {
            String name = dValues.values()[i].toString().toLowerCase();
            if (!config.getConfig().isSet(ba + "." + name)) {
                config.setDouble(ba, name, dValues.values()[i].value);
            }
        }
        for (int i = 0; i < iValues.values().length; i++) {
            String name = iValues.values()[i].toString().toLowerCase();
            if (!config.getConfig().isSet(ba + "." + name)) {
                config.setInt(ba, name, iValues.values()[i].value);
            }
        }
        config.saveConfig();
    }
}
