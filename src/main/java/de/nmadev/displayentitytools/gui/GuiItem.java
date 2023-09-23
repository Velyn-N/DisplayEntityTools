package de.nmadev.displayentitytools.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GuiItem {

    private final ItemStack itemStack;
    private final Map<ClickType, Runnable> actions = new HashMap<>();

    public GuiItem(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setAction(ClickType clickType, Runnable action) {
        actions.put(clickType, action);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void updateMeta(Function<ItemMeta, ItemMeta> updater) {
        this.itemStack.setItemMeta(updater.apply(this.itemStack.getItemMeta()));
    }

    public void onClick(ClickType clickType) {
        if (actions.containsKey(clickType)) {
            actions.get(clickType).run();
        }
    }
}
