package de.nmadev.textdisplaytools;

import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.HashMap;
import java.util.Optional;

public class SelectionCache {

    private final HashMap<Player, TextDisplay> selectionMap = new HashMap<>();

    public void setSelection(Player player, TextDisplay textDisplay) {
        selectionMap.put(player, textDisplay);
    }

    public Optional<TextDisplay> getSelectedTextDisplay(Player player) {
        return Optional.ofNullable(selectionMap.get(player));
    }

    public Optional<TextDisplay> deselectTextDisplay(Player player) {
        return Optional.ofNullable(selectionMap.remove(player));
    }

    public void clearSelectionMap() {
        selectionMap.clear();
    }
}
