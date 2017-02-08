package me.vitikc.heroes.listeners;

import me.vitikc.heroes.HeroTypes;
import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.HeroesManager;
import me.vitikc.heroes.abilities.HeroesAbilitiesManager;
import me.vitikc.heroes.abilities.HeroesAbilityUtils;
import me.vitikc.heroes.cooldown.HeroesCooldown;
import me.vitikc.heroes.cooldown.HeroesCooldownValues;
import me.vitikc.heroes.events.HeroesStunEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MainHand;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public class HeroesItemAbilitiesListener implements Listener {
    private HeroesMain plugin;
    private HeroesManager heroesManager;
    private HeroesAbilityUtils abilityUtils;
    private HeroesAbilitiesManager abilitiesManager;
    private HeroesCooldown cooldown;
    private HeroesCooldownValues cooldownValues;

    public HeroesItemAbilitiesListener(HeroesMain plugin){
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
        if (!event.getHand().equals(EquipmentSlot.HAND)){
            return;
        }
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
            case YURNERO:
                if (player.getInventory().getItemInMainHand().getType() == Material.COAL){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.YURNEROATTACK.toString())<=0){
                        abilitiesManager.getYurnero().Attack(player);
                        player.sendMessage("Attackink");
                        cooldown.putCooldown(player,
                            HeroesCooldownValues.Values.YURNEROATTACK.toString(),
                            HeroesCooldownValues.Values.YURNEROATTACK.get());
                    }
                    else {
                        player.sendMessage("Cooldown " +
                            cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.YURNEROATTACK.toString())/
                            cooldownValues.SECONDS
                        );
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.EMERALD){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.YURNERODEFENSE.toString())<=0){
                        abilitiesManager.getYurnero().Defense(player);
                        cooldown.putCooldown(player,
                            HeroesCooldownValues.Values.YURNERODEFENSE.toString(),
                            HeroesCooldownValues.Values.YURNERODEFENSE.get());
                    }
                    else {
                        player.sendMessage("Cooldown " +
                            cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.YURNERODEFENSE.toString()) /
                            cooldownValues.SECONDS
                        );
                    }
                }
                break;
            case SAMURAI:
                if (player.getInventory().getItemInMainHand().getType() == Material.COAL){
                    if (cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIATTACK.name())<=0)
                    abilitiesManager.getSamurai().Attack(player);
                    else
                        player.sendMessage("Cooldown "+ cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIATTACK.name()) /
                            cooldownValues.SECONDS);
                } else if (player.getInventory().getItemInMainHand().getType() == Material.STICK){
                    if (cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIULTIMATE.name())<=0) {
                        abilitiesManager.getSamurai().Ultimate(player);
                        cooldown.putCooldown(player,
                            HeroesCooldownValues.Values.SAMURAIULTIMATE.name(),
                            HeroesCooldownValues.Values.SAMURAIULTIMATE.get());
                    } else
                        player.sendMessage("Cooldown "+ cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIULTIMATE.name()) /
                            cooldownValues.SECONDS);
                }else if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND){
                    if (cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIDEFENSE.name())<=0) {
                        abilitiesManager.getSamurai().Defense(player);
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.SAMURAIDEFENSE.name(),
                                HeroesCooldownValues.Values.SAMURAIDEFENSE.get());
                    } else
                        player.sendMessage("Cooldown "+ cooldown.getCooldown(player,HeroesCooldownValues.Values.SAMURAIULTIMATE.name()) /
                                cooldownValues.SECONDS);
                }
                break;
            case DRAGON_KNIGHT:
                if (player.getInventory().getItemInMainHand().getType() == Material.COAL) {
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.DRAGONKNIGHTATTACK.toString())<=0) {
                        abilitiesManager.getDragonKnight().Attack(player);
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.DRAGONKNIGHTATTACK.toString(),
                                HeroesCooldownValues.Values.DRAGONKNIGHTATTACK.get());
                    } else {
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                        HeroesCooldownValues.Values.DRAGONKNIGHTATTACK.toString()) /
                                        cooldownValues.SECONDS
                        );
                    }
                } else if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.DRAGONKNIGHTULTIMATE.toString())<=0) {
                        abilitiesManager.getDragonKnight().Ultimate(player);
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.DRAGONKNIGHTULTIMATE.toString(),
                                HeroesCooldownValues.Values.DRAGONKNIGHTULTIMATE.get());
                    } else {
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                        HeroesCooldownValues.Values.DRAGONKNIGHTULTIMATE.toString()) /
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
            case YURNERO:
                if (player.getInventory().getItemInMainHand().getType() == Material.STICK){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.YURNEROULTIMATE.toString())<=0){
                        abilitiesManager.getYurnero().Ultimate(player,target);
                        player.sendMessage("Ultimating");
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.YURNEROULTIMATE.toString(),
                                HeroesCooldownValues.Values.YURNEROULTIMATE.get());
                    }
                    else {
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                        HeroesCooldownValues.Values.YURNEROULTIMATE.toString())/
                                        cooldownValues.SECONDS
                        );
                    }
                }
                break;
            case DRAGON_KNIGHT:
                if (player.getInventory().getItemInMainHand().getType() == Material.STICK){
                    if (cooldown.getCooldown(player,
                            HeroesCooldownValues.Values.DRAGONKNIGHTSTUN.toString())<=0) {
                        HeroesStunEvent heroesStunEvent = new HeroesStunEvent(player,target);
                        plugin.getServer().getPluginManager().callEvent(heroesStunEvent);
                        if (!event.isCancelled()) abilitiesManager.getDragonKnight().Stun(target);
                        cooldown.putCooldown(player,
                                HeroesCooldownValues.Values.DRAGONKNIGHTSTUN.toString(),
                                HeroesCooldownValues.Values.DRAGONKNIGHTSTUN.get());
                    } else {
                        player.sendMessage("Cooldown " +
                                cooldown.getCooldown(player,
                                        HeroesCooldownValues.Values.DRAGONKNIGHTSTUN.toString())/
                                        cooldownValues.SECONDS
                        );
                    }
                }
                break;
            default:
                break;
        }

    }
}
