package de.nmadev.displayentitytools.command.subcommands.position;

import de.nmadev.displayentitytools.DisplayEntityTools;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import de.nmadev.displayentitytools.position.PositionHelper;
import de.nmadev.displayentitytools.position.PositionModificationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetToolSubCommand extends PlayerOnlyBaseCommand {

    private final DisplayEntityTools plugin;

    public GetToolSubCommand(Logger logger, DisplayEntityTools plugin) {
        super("get-tool", logger, USE_PERMISSION);
        this.plugin = plugin;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        if (args.length > 0) {
            ItemStack itemStack = switch (PositionModificationType.valueOf(args[0])) {
                case X_AXIS ->
                        getItemWithDefaultLore("Move-X Tool", PositionModificationType.X_AXIS);
                case Y_AXIS ->
                        getItemWithDefaultLore("Move-Y Tool", PositionModificationType.Y_AXIS);
                case Z_AXIS ->
                        getItemWithDefaultLore("Move-Z Tool", PositionModificationType.Z_AXIS);
                case ROTATION ->
                        getItemWithDefaultLore("Rotation Tool", PositionModificationType.ROTATION);
                case TILT ->
                        getItemWithDefaultLore("Tilt Tool", PositionModificationType.TILT);
            };
            return sendItemToPlayerInventory(player, itemStack);
        }
        sendPrefixedReply(player, Component.text("Invalid Name for Tool.", NamedTextColor.RED));
        return false;
    }

    @Override
    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return Arrays.stream(PositionModificationType.values())
                     .map(PositionModificationType::toString)
                     .toList();
    }

    private ItemStack getItemWithDefaultLore(String displayName, PositionModificationType type) {
        ItemStack itemStack = new ItemStack(Material.STICK);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(displayName, NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        itemMeta.lore(List.of(
                Component.empty(),
                Component.text("Shift-Scroll", NamedTextColor.YELLOW)
                         .append(Component.text(" or ", NamedTextColor.GOLD))
                         .append(Component.text("Right/Left-Click", NamedTextColor.YELLOW))
                         .append(Component.text(" to use.", NamedTextColor.GOLD)),
                Component.empty(),
                Component.text("Use ", NamedTextColor.GOLD)
                         .append(Component.text("/det settings", NamedTextColor.YELLOW))
                         .append(Component.text(" to change sensitivity.", NamedTextColor.GOLD)),
                Component.empty(),
                Component.text("Part of DisplayEntityTools", NamedTextColor.DARK_GRAY)
                         .decorate(TextDecoration.UNDERLINED)
        ));

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(PositionHelper.getPositionModTypeKey(plugin),
                PersistentDataType.INTEGER,
                type.ordinal());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private boolean sendItemToPlayerInventory(Player player, ItemStack itemStack) {
        Inventory inv = player.getInventory();
        int currentSlot = player.getInventory().getHeldItemSlot();
        ItemStack currentItem = inv.getItem(currentSlot);

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            inv.setItem(currentSlot, itemStack);
            return true;
        }

        Map<Integer, ItemStack> remainingItems = inv.addItem(itemStack);
        if (remainingItems.isEmpty()) {
            return true;
        }
        sendPrefixedReply(player, Component.text("Your Inventory is full!", NamedTextColor.RED));
        return false;
    }
}
