package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroTypes;
import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.HeroesManager;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesItemListener implements Listener {
    private HeroesMain plugin;
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;

    public HeroesItemListener(HeroesMain plugin){
        this.plugin = plugin;
        heroesManager = plugin.getHeroesManager();
        abilityUtils = plugin.getAbilityUtils();
        abilitiesManager = plugin.getAbilitiesManager();
        cooldown = plugin.getCooldown();
        cooldownValues = plugin.getCooldownValues();
    }


    @EventHandler
    private void onItemUseEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        if (!heroesManager.isSet(player))
            return;
        HeroTypes type = heroesManager.getPlayerHero(player);
        switch (type){
            case BARBARIAN:
                break;
            case LEGION_COMMANDER:
                if(player.getInventory().getItemInMainHand().getType() == Material.DIAMOND){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.LEGIONCOMMANDERATTACK.toString())<=0) {
                        abilitiesManager.getLegionCommander().Attack(player);
                        cooldown.putCooldown(player,
                            HeroesCooldownValues.Values.LEGIONCOMMANDERATTACK.toString(),
                            HeroesCooldownValues.Values.LEGIONCOMMANDERATTACK.get());
                    } else {
                        player.sendMessage("Cooldown " +
                            cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.LEGIONCOMMANDERATTACK.toString())/
                            cooldownValues.SECONDS
                        );
                    }
                }
                break;
            default:
                break;
        }
    }
    @EventHandler
    private void onItemUseOnEntity(PlayerInteractAtEntityEvent event){
        if (event.getHand()!= EquipmentSlot.HAND)
            return;
        if (!(event.getRightClicked() instanceof  Player)){
            return;
        }
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();
        if (!heroesManager.isSet(player)){
            return;
        }
        HeroTypes type = heroesManager.getPlayerHero(player);
        switch (type){
            case BARBARIAN:
                if (player.getInventory().getItemInMainHand().getType() == Material.STICK){
                    if (cooldown.getCooldown(
                            player,
                            HeroesCooldownValues.Values.BARBARIANATTACK.toString())<=0) {
                        abilitiesManager.getBarbarian().Attack(player, target);
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.BARBARIANATTACK.toString(),
                                HeroesCooldownValues.Values.BARBARIANATTACK.get());
                    } else {
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                    HeroesCooldownValues.Values.BARBARIANATTACK.toString())/
                                    cooldownValues.SECONDS);
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.EMERALD){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.BARBARIANULTIMATE.toString())<=0)
                        abilitiesManager.getBarbarian().Ultimate(player, target);
                    else
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                        HeroesCooldownValues.Values.BARBARIANULTIMATE.toString())/
                                        cooldownValues.SECONDS);
                }
                break;
            case LEGION_COMMANDER:
                if (player.getInventory().getItemInMainHand().getType() == Material.COAL){
                    if (abilitiesManager.getLegionCommander().isPlayerInDuel(player))
                        return;
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.LEGIONCOMMANDERULTIMATE.toString())<=0) {
                        abilitiesManager.getLegionCommander().Ultimate(player, target);
                        cooldown.putCooldown(player,
                            HeroesCooldownValues.Values.LEGIONCOMMANDERULTIMATE.toString(),
                            HeroesCooldownValues.Values.LEGIONCOMMANDERULTIMATE.get());
                    }
                    else
                        player.sendMessage("Cooldown " +
                            cooldown.getCooldown(player,
                                HeroesCooldownValues.Values.LEGIONCOMMANDERULTIMATE.toString())/
                                cooldownValues.SECONDS);
                }
                break;
            default:
                break;
        }

    }
}
