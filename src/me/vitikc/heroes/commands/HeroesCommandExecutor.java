package me.vitikc.heroes.commands;

import me.vitikc.heroes.HeroTypes;
import me.vitikc.heroes.HeroesMain;
import me.vitikc.heroes.HeroesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 20/Jan/17.
 */
public class HeroesCommandExecutor implements CommandExecutor{
    private HeroesMain plugin;
    private HeroesManager heroesManager;

    public HeroesCommandExecutor(HeroesMain plugin){
        this.plugin = plugin;
        heroesManager = plugin.getHeroesManager();
    }

    public void showHeroesList(Player player){
        for (int i = 0;i < HeroTypes.values().length; i++){
            player.sendMessage(i+1 + "." + HeroTypes.values()[i].toString());
        }
    }

    @Override
    public  boolean onCommand (CommandSender sender, Command cmd, String label, String args[]){
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry command can be sended from player only");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0){
            //print info
        } else
        if (args.length == 1){
            if (args[0].equalsIgnoreCase("list")){
                showHeroesList(player);
            } else if (args[0].equalsIgnoreCase("forget")){
                heroesManager.removePlayer(player);
            }
        } else
        if (args.length == 2){
            if (args[0].equalsIgnoreCase("choose")){
                int num = Integer.parseInt(args[1]);
                if (heroesManager.isSet(player)){
                    player.sendMessage("You already choosed hero - "
                            + heroesManager.getPlayerHero(player).toString());
                    return true;
                }
                heroesManager.addPlayer(player, HeroTypes.values()[num-1]);
                player.sendMessage("Choosed!");
            }
        }
        return true;
    }
}
