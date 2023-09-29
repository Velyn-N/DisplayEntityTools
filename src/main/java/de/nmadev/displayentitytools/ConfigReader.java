package de.nmadev.displayentitytools;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigReader {

    private FileConfiguration config;

    public ConfigReader(FileConfiguration config) {
        this.config = config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public List<String> getCommandAliases() {
        return config.getStringList("command-aliases");
    }

    public boolean isDebugMode() {
        return config.getBoolean("debug");
    }
}
