package de.nmadev.displayentitytools;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class SettingCache {

    private final HashMap<Player, PlayerSettings> settingsMap = new HashMap<>();

    public PlayerSettings getSettings(Player player) {
        return settingsMap.computeIfAbsent(player, p -> new PlayerSettings());
    }

    public Optional<PlayerSettings> unsetSettings(Player player) {
        return Optional.ofNullable(settingsMap.remove(player));
    }
}
