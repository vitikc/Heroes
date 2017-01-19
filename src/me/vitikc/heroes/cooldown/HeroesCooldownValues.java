package me.vitikc.heroes.cooldown;

import java.util.HashMap;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldownValues {
    private HashMap<String, Integer> values = new HashMap<>();

    public final int SECONDS = 1000;

    public final String barbarianAttack = "barbarianattack";
    public final String barbarianDefense = "barbariandefense";
    public final String barbarianUltimate = "barbarianultimate";
    private int barbarianAttackCooldown = 30*SECONDS;
    private int barbarianDefenseCooldown = 1*SECONDS;
    private int barbarianUltimateCooldown = 30*SECONDS;

    public final String legionCommanderAttack = "legioncommanderattack";
    public final String legionCommanderDefense = "legioncommanderdefense";
    public final String legionCommanderUltimate = "legioncommanderultimate";
    private int legionCommanderAttackCooldown = 30*SECONDS;
    private int legionCommanderDefenseCooldown = 1*SECONDS;
    private int legionCommanderUltimateCooldown = 40*SECONDS;

    public HeroesCooldownValues(){
        setDefaults();
    }

    private void setDefaults(){
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

    //public void loadFromFile(){};

}
