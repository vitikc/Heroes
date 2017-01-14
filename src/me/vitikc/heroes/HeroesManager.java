package me.vitikc.heroes;

import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesManager {
    private HashMap<String, HeroTypes> playersHeroes = new HashMap<>();

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
    public HashMap<String, HeroTypes> getPlayersHeroes(){
        return playersHeroes;
    }
}
