package de.nmadev.displayentitytools;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class SelectionCache {

    private final HashMap<Player, DisplayEntity> selectionMap = new HashMap<>();

    public void setSelection(Player player, DisplayEntity displayEntity) {
        selectionMap.put(player, displayEntity);
    }

    public Optional<DisplayEntity> getSelectedDisplayEntity(Player player) {
        return Optional.ofNullable(selectionMap.get(player));
    }

    public Optional<DisplayEntity> deselectDisplayEntity(Player player) {
        return Optional.ofNullable(selectionMap.remove(player));
    }
}
