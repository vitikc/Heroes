package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.items.HeroesItemsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by Vitikc on 30/Jan/17.
 */
public class HeroesItemListener implements Listener {
    private HeroesMain plugin;
    private HeroesItemsManager items;

    public HeroesItemListener(HeroesMain plugin){
        this.plugin = plugin;
        this.items = plugin.getItemsManager();
    }

    @EventHandler
    public void onPlayerUseItem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!event.getHand().equals(EquipmentSlot.HAND)){
            return;
        }
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS){
            items.teleportItem(player);
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.ARROW){
            items.addBase(player.getLocation());
        }
    }
}
