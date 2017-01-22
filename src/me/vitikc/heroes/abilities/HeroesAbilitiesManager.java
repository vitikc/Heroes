package me.vitikc.heroes.abilities;

import me.vitikc.heroes.HeroesMain;

/**
 * Created by Vitikc on 12/Jan/17.
 */
public class HeroesAbilitiesManager {
    private HeroesMain plugin;
    private HeroesBarbarianAbilities barbarian;
    private HeroesLegionCommanderAbilities legionCommander;
    private HeroesYurneroAbilities yurnero;
    public HeroesAbilitiesManager(HeroesMain plugin) {
        this.plugin = plugin;
        barbarian = new HeroesBarbarianAbilities(plugin);
        legionCommander = new HeroesLegionCommanderAbilities(plugin);
        yurnero = new HeroesYurneroAbilities(plugin);
    }

    public HeroesBarbarianAbilities getBarbarian() {
        return barbarian;
    }

    public HeroesLegionCommanderAbilities getLegionCommander() {
        return legionCommander;
    }

    public HeroesYurneroAbilities getYurnero(){
        return yurnero;
    }
}
