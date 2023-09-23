package de.nmadev.displayentitytools.command.subcommands.position;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class GetToolSubCommand extends PlayerOnlyBaseCommand {

    public GetToolSubCommand(Logger logger) {
        super("get-tool", logger, USE_PERMISSION);
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        if (args.length > 0) {
            ItemStack itemStack = switch (args[0]) {
                case "rotation" ->
                        getItemWithDefaultLore(Component.text("TDT Rotation Tool", NamedTextColor.GOLD));
                case "move-x" ->
                        getItemWithDefaultLore(Component.text("TDT Move-X Tool", NamedTextColor.GOLD));
                case "move-y" ->
                        getItemWithDefaultLore(Component.text("TDT Move-Y Tool", NamedTextColor.GOLD));
                case "move-z" ->
                        getItemWithDefaultLore(Component.text("TDT Move-Z Tool", NamedTextColor.GOLD));
                default -> null;
            };

            if (itemStack != null) {
                return sendItemToPlayerInventory(player, itemStack);
            }
        }
        sendPrefixedReply(player, Component.text("Invalid Name for Tool.", NamedTextColor.RED));
        return false;
    }

    @Override
    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return List.of("rotation", "move-x", "move-y", "move-z");
    }

    private ItemStack getItemWithDefaultLore(Component displayName) {
        ItemStack itemStack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(List.of(
                Component.text("Scroll Up/Down or Right/Left-Click to use.", NamedTextColor.YELLOW),
                Component.text("Use ", NamedTextColor.YELLOW)
                        .append(Component.text("/tdt settings", NamedTextColor.GREEN))
                        .append(Component.text(" to change sensitivity.", NamedTextColor.YELLOW)),
                Component.empty(),
                Component.text("Part of DisplayEntityTools", NamedTextColor.GOLD)
        ));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private boolean sendItemToPlayerInventory(Player player, ItemStack itemStack) {
        Inventory inv = player.getInventory();
        int currentSlot = player.getInventory().getHeldItemSlot();
        ItemStack currentItem = inv.getItem(currentSlot);

        // Check if the current slot is empty
        if (currentItem == null || currentItem.getType() == Material.AIR) {
            inv.setItem(currentSlot, itemStack);
            return true;
        }

        // Try to move the existing item to another slot
        Map<Integer, ItemStack> remainingItems = inv.addItem(currentItem);

        // If there is space to move the existing item
        if (remainingItems.isEmpty()) {
            inv.setItem(currentSlot, itemStack);
        } else {
            // Inventory is full
            player.sendMessage("Your inventory is full!");
            return false;
        }
        return true;
    }
}
