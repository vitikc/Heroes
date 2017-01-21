package me.vitikc.heroes.cooldown;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldownValues {

    private HeroesConfigManager config;

    public final int SECONDS = 1000;

    public enum CooldownsValues{
        BARBARIANATTACK(30000),
        BARBARIANDEFENSE(1000),
        BARBARIANULTIMATE(30000),
        LEGIONCOMMANDERATTACK(1000),
        LEGIONCOMMANDERDEFENSE(1000),
        LEGIONCOMMANDERULTIMATE(40000);
        private int value;
        CooldownsValues(int value){
            this.value = value;
        }
        public int get(){
            return this.value;
        }
    }

    public HeroesCooldownValues(HeroesMain plugin){
        config = plugin.getConfigManager();
        loadFromConfig();
        setDefaultConfig();
    }

    public void loadFromConfig(){
        String cd = "cooldowns";
        for (int i = 0; i < CooldownsValues.values().length; i++){
            String name = CooldownsValues.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(cd + "." + name))
                CooldownsValues.values()[i].value = config.getInt(cd, name)*SECONDS;
        }
    }
    public void setDefaultConfig(){
        String cd = "cooldowns";
        for (int i = 0; i < CooldownsValues.values().length; i++){
            String name = CooldownsValues.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(cd + "." + name)){
                config.setInt(cd, name, CooldownsValues.values()[i].value/SECONDS);
            }
        }
        config.saveConfig();
    }

}
