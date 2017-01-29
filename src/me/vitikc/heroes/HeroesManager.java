package me.vitikc.heroes;

import me.vitikc.heroes.config.HeroesConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Vitikc on 09/Jan/17.
 */

// TODO: 21/Jan/17 Saving player heroes too config file; 
public class HeroesManager {
    private HeroesMain plugin;
    private HeroesConfigManager config;

    private static HashMap<String, HeroTypes> playersHeroes = new HashMap<>();

    public HeroesManager(HeroesMain plugin){
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        loadPlayerData();
    }

    public void addPlayer(Player player, HeroTypes type){
        playersHeroes.put(player.getUniqueId().toString(), type);
        savePlayerData();
    }
    public void removePlayer(Player player){
        playersHeroes.remove(player.getUniqueId().toString());
        savePlayerData();
    }
    public HeroTypes getPlayerHero(Player player){
        return playersHeroes.get(player.getUniqueId().toString());
    }
    public boolean isSet(Player player){
        return playersHeroes.containsKey(player.getUniqueId().toString());
    }
    public static HashMap<String, HeroTypes> getPlayersHeroes(){
        return playersHeroes;
    }
    public void savePlayerData(){
        for (String name : playersHeroes.keySet()) {
            config.getData().set(name,playersHeroes.get(name).getName());
        }
        config.savaData();
    }
    public void loadPlayerData(){
        for (String name: config.getData().getKeys(true)){
            String tname = config.getData().getString(name);
            HeroTypes type = HeroTypes.getType(tname);
            if (type == null)
                return;
            playersHeroes.put(name,type);
        }
    }
}
