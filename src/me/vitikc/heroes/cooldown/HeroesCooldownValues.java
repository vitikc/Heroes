package me.vitikc.heroes.cooldown;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.config.HeroesConfigManager;

import java.util.HashMap;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldownValues {
    private HeroesConfigManager config;

    private HashMap<String, Integer> values = new HashMap<>();

    public final int SECONDS = 1000;

    public final String barbarianAttack = "barbarianattackcooldown";
    public final String barbarianDefense = "barbariandefensecooldown";
    public final String barbarianUltimate = "barbarianultimatecooldown";
    private int barbarianAttackCooldown = 30*SECONDS;
    private int barbarianDefenseCooldown = 1*SECONDS;
    private int barbarianUltimateCooldown = 30*SECONDS;

    public final String legionCommanderAttack = "legioncommanderattackcooldown";
    public final String legionCommanderDefense = "legioncommanderdefensecooldown";
    public final String legionCommanderUltimate = "legioncommanderultimatecooldown";
    private int legionCommanderAttackCooldown = 1*SECONDS;
    private int legionCommanderDefenseCooldown = 1*SECONDS;
    private int legionCommanderUltimateCooldown = 40*SECONDS;

    public HeroesCooldownValues(HeroesMain plugin){
        config = plugin.getConfigManager();
        setDefaultConfig();
        loadFromConfig();
        setValues();
    }

    private void setValues(){
        values.put(barbarianAttack,barbarianAttackCooldown);
        values.put(barbarianDefense,barbarianDefenseCooldown);
        values.put(barbarianUltimate,barbarianUltimateCooldown);
        values.put(legionCommanderAttack,legionCommanderAttackCooldown);
        values.put(legionCommanderDefense,legionCommanderDefenseCooldown);
        values.put(legionCommanderUltimate,legionCommanderUltimateCooldown);
    }



    public HashMap<String, Integer> getValues(){
        return  values;
    }

    public void loadFromConfig(){
        String lc = "LegionCommander";
        String ba = "Barbarian";
        if (config.getConfig().isSet(ba)){
            barbarianAttackCooldown = config.getInt(lc, barbarianAttack) * SECONDS;
            barbarianDefenseCooldown = config.getInt(lc, barbarianDefense) * SECONDS;
            barbarianUltimateCooldown = config.getInt(lc, barbarianUltimate) * SECONDS;
        }
        if (config.getConfig().isSet(lc)){
            legionCommanderAttackCooldown = config.getInt(lc, legionCommanderAttack) * SECONDS;
            legionCommanderDefenseCooldown = config.getInt(lc, legionCommanderDefense) * SECONDS;
            legionCommanderUltimateCooldown = config.getInt(lc, legionCommanderUltimate) * SECONDS;
        }
    }
    public void setDefaultConfig(){
        String lc = "LegionCommander";
        String ba = "Barbarian";
       // if (!config.getConfig().isSet(ba)) {
            config.setInt(ba, barbarianAttack, barbarianAttackCooldown/SECONDS);
            config.setInt(ba, barbarianDefense, barbarianDefenseCooldown/SECONDS);
            config.setInt(ba, barbarianUltimate, barbarianUltimateCooldown/SECONDS);
       // }
       // if (!config.getConfig().isSet(lc)) {
            config.setInt(lc, legionCommanderAttack, legionCommanderAttackCooldown/SECONDS);
            config.setInt(lc, legionCommanderDefense, legionCommanderDefenseCooldown/SECONDS);
            config.setInt(lc, legionCommanderUltimate, legionCommanderUltimateCooldown/SECONDS);
        //}
        config.saveConfig();
    }

}
