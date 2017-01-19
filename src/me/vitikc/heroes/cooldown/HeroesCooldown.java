package me.vitikc.heroes.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Vitikc on 19/Jan/17.
 */
public class HeroesCooldown {
    private static Table<String, String, Long> cooldowns = HashBasedTable.create();

    public long getCooldown(Player player, String key) {
        return calculateRemainder(cooldowns.get(player.getName(), key));
    }

    public long setCooldown(Player player, String key, long delay) {
        return calculateRemainder(cooldowns.put(player.getName(), key, System.currentTimeMillis() + delay));
    }

    public boolean putCooldown(Player player, String key, long delay) {
        if (getCooldown(player, key) <= 0) {
            setCooldown(player, key, delay);
            return true;
        }
        return false;
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}