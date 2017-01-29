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
    private File dataFile;
    private static FileConfiguration config;
    private static FileConfiguration data;

    public HeroesConfigManager(HeroesMain plugin){
        this.plugin = plugin;
        createFiles();
        loadConfig();
        loadData();
    }

    private void createFiles(){
        configFile = new File(plugin.getDataFolder(), "config.yml");
        dataFile = new File(plugin.getDataFolder(), "players.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
        if (!configFile.exists()) {
            try {
                config.save(configFile);
                plugin.getLogger().info("Generating new config file...");
            } catch (IOException ex) {
                throw new IllegalStateException("Unable to create config file ", ex);
            }
        }
        if (!dataFile.exists()){
            try {
                data.save(dataFile);
                plugin.getLogger().info("Generating new data file...");
            } catch (IOException ex) {
                throw new IllegalStateException("Unable to create data file ", ex);
            }
        }
    }

    public void savaData(){
        try {
            data.save(dataFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        try {
            config.save(configFile);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadData(){
        try {
            data.load(dataFile);
        } catch (Exception e){
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

    public FileConfiguration getData(){
        return data;
    }

    public int getInt(String arg1, String arg2){
        return config.getInt(arg1 + "." + arg2);
    }
    public void setInt(String arg1, String arg2, int value){
        config.set(arg1 + "." + arg2, value);
    }
    public double getDouble(String arg1, String arg2){
        return config.getDouble(arg1 + "." + arg2);
    }
    public void setDouble(String arg1, String arg2, double value){
        config.set(arg1 + "." + arg2, value);
    }
}
