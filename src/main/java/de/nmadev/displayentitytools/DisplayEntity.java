package de.nmadev.displayentitytools;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;

import java.util.Optional;

public class DisplayEntity {

    private final Display wrappedEntity;

    private final boolean isTextDisplay;
    private final boolean isBlockDisplay;
    private final boolean isItemDisplay;

    public DisplayEntity(Display display) {
        this.wrappedEntity = display;
        isTextDisplay = wrappedEntity instanceof TextDisplay;
        isBlockDisplay = wrappedEntity instanceof BlockDisplay;
        isItemDisplay = wrappedEntity instanceof ItemDisplay;
    }



    public Optional<TextDisplay> asTextDisplayOptional() {
        return Optional.ofNullable(asTextDisplay());
    }

    public Optional<BlockDisplay> asBlockDisplayOptional() {
        return Optional.ofNullable(asBlockDisplay());
    }

    public Optional<ItemDisplay> asItemDisplayOptional() {
        return Optional.ofNullable(asItemDisplay());
    }

    public TextDisplay asTextDisplay() {
        return isTextDisplay ? (TextDisplay) wrappedEntity : null;
    }

    public BlockDisplay asBlockDisplay() {
        return isBlockDisplay ? (BlockDisplay) wrappedEntity : null;
    }

    public ItemDisplay asItemDisplay() {
        return isItemDisplay ? (ItemDisplay) wrappedEntity : null;
    }

    public Display getDisplay() {
        return wrappedEntity;
    }



    public boolean isTextDisplay() {
        return isTextDisplay;
    }

    public boolean isBlockDisplay() {
        return isBlockDisplay;
    }

    public boolean isItemDisplay() {
        return isItemDisplay;
    }
}
