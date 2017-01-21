package me.vitikc.heroes.cooldown;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldownValues {

    private HeroesConfigManager config;

    public final int SECONDS = 1000;

    public enum Values {
        BARBARIANATTACK(30000),
        BARBARIANDEFENSE(1000),
        BARBARIANULTIMATE(30000),
        LEGIONCOMMANDERATTACK(1000),
        LEGIONCOMMANDERDEFENSE(1000),
        LEGIONCOMMANDERULTIMATE(40000);
        private int value;
        Values(int value){
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
        for (int i = 0; i < Values.values().length; i++){
            String name = Values.values()[i].toString().toLowerCase();
            if(config.getConfig().isSet(cd + "." + name))
                Values.values()[i].value = config.getInt(cd, name)*SECONDS;
        }
    }
    public void setDefaultConfig(){
        String cd = "cooldowns";
        for (int i = 0; i < Values.values().length; i++){
            String name = Values.values()[i].toString().toLowerCase();
            if(!config.getConfig().isSet(cd + "." + name)){
                config.setInt(cd, name, Values.values()[i].value/SECONDS);
            }
        }
        config.saveConfig();
    }

}
