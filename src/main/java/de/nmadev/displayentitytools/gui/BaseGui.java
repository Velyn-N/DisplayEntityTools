package de.nmadev.displayentitytools.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseGui implements InventoryHolder {

    private final Inventory inventory;
    private final Map<Integer, GuiItem> items = new HashMap<>();

    public BaseGui(int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, Component.text(title));
    }

    public void openForPlayer(Player player) {
        player.openInventory(getInventory());
    }

    protected void setItem(int slot, GuiItem item) {
        inventory.setItem(slot, item.getItemStack());
        items.put(slot, item);
    }

    protected GuiItem getItem(int slot) {
        return items.get(slot);
    }

    public void handleClick(int slot, ClickType clickType) {
        GuiItem item = getItem(slot);
        if (item != null) {
            item.onClick(clickType);
        }
        update();
    }

    public void update() {
        // Do nothing by default.
        // Subclasses can override this to add specific update behavior.
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
