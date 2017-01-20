package me.vitikc.heroes.config;

import me.vitikc.heroes.HeroesMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vitikc on 20/Jan/17.
 */
public class HeroesConfigManager {
    private HeroesMain plugin;

    private File configFile;
    private static FileConfiguration config;

    public HeroesConfigManager(HeroesMain plugin){
        this.plugin = plugin;
        createConfig();
        loadConfig();
    }

    private void createConfig(){
        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            try {
                config.save(configFile);
                plugin.getLogger().info("Generating new config file...");
            } catch (IOException ex) {
                throw new IllegalStateException("Unable to create config file ", ex);
            }
        }
    }

    public void saveConfig(){
        try {
            config.save(configFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            config.load(configFile);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        return config;

    }

    public int getInt(String hero, String name){
        return config.getInt(hero + "." + name);
    }
    public void setInt(String hero, String name, int value){
        config.set(hero + "." + name, value);
        saveConfig();
    }
    public double getDouble(String hero, String name){
        return config.getDouble(hero + "." + name);
    }
    public void setDouble(String hero, String name, double value){
        config.set(hero + "." + name, value);
        saveConfig();
    }
}
