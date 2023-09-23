package de.nmadev.displayentitytools.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        int slot = event.getSlot();
        ClickType clickType = event.getClick();

        // Only process the event if the inventory is a BaseGui
        if (inventory != null && inventory.getHolder() instanceof BaseGui window) {
            event.setCancelled(true);

            window.handleClick(slot, clickType);
        }
    }
}
