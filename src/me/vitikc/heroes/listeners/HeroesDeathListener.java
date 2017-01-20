package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * Created by Vitikc on 20/Jan/17.
 */
public class HeroesDeathListener implements Listener {
    private HeroesMain plugin;
    private HeroesAbilitiesManager abilitiesManager;


    public HeroesDeathListener(HeroesMain plugin){
        this.plugin = plugin;
        abilitiesManager = plugin.getAbilitiesManager();
    }

    private Player getKeyByValue(Player value){
        for (Player p : abilitiesManager.getLegionCommander().getDuels().keySet()){
            if(abilitiesManager.getLegionCommander().getDuels().get(p).equals(value)){
                return p;
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (abilitiesManager.getLegionCommander().getDamage(player) > 0){
            abilitiesManager.getLegionCommander().setDamage(player, 0);
        }
        if (!abilitiesManager.getLegionCommander().isPlayerInDuel(player)) {
            return;
        }
        if (abilitiesManager.getLegionCommander().getDuels().containsKey(player)){
            abilitiesManager.getLegionCommander().addDamage(abilitiesManager.getLegionCommander().getDuels().get(player));
            abilitiesManager.getLegionCommander().getDuels().get(player).sendMessage("You win duel!");
        } else if (abilitiesManager.getLegionCommander().getDuels().containsValue(player)){
            abilitiesManager.getLegionCommander().addDamage(getKeyByValue(player));
            getKeyByValue(player).sendMessage("You win duel!");
        }
    }
}
