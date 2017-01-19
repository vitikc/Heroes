package me.vitikc.heroes.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.entity.Player;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldown {
    private static Table<String, String, Long> cooldowns = HashBasedTable.create();

    public static long getCooldown(Player player, String key) {
        return calculateRemainder(cooldowns.get(player.getName(), key));
    }

    public static long setCooldown(Player player, String key, long delay) {
        return calculateRemainder(cooldowns.put(player.getName(), key, System.currentTimeMillis() + delay));
    }

    public static boolean putCooldown(Player player, String key, long delay) {
        if (getCooldown(player, key) <= 0) {
            setCooldown(player, key, delay);
            return true;
        }
        return false;
    }

    private static long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}