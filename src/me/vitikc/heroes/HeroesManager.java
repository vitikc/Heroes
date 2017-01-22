package me.vitikc.heroes;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Vitikc on 09/Jan/17.
 */

// TODO: 21/Jan/17 Saving player heroes too config file; 
public class HeroesManager {
    private static HashMap<String, HeroTypes> playersHeroes = new HashMap<>();

    public void addPlayer(Player player, HeroTypes type){
        playersHeroes.put(player.getUniqueId().toString(), type);
    }
    public void removePlayer(Player player){
        playersHeroes.remove(player.getUniqueId().toString());
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
}
