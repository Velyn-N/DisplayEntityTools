package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.DisplayEntity;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CreateSubCommand extends PlayerOnlyBaseCommand {

    private final SelectionCache selectionCache;

    public CreateSubCommand(Logger logger, SelectionCache selectionCache) {
        super("create", logger, USE_PERMISSION);
        this.selectionCache = selectionCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        if (args.length < 1) {
            sendPrefixedReply(player, Component.text("You need to specify a DisplayEntity Type!",
                                                     NamedTextColor.RED));
            return false;
        }

        Location playerLocation = player.getLocation();
        Display display = null;

        switch (args[0]) {
            case "TextDisplay" -> {
                Entity entity = playerLocation.getWorld().spawnEntity(playerLocation, EntityType.TEXT_DISPLAY);
                if (entity instanceof TextDisplay textDisplay) {
                    String initialText = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    if (initialText.isEmpty() || initialText.isBlank()) {
                        initialText = "Your new TextDisplay";
                    }
                    textDisplay.text(Component.text(initialText));
                    display = textDisplay;
                }
            }
            case "BlockDisplay" -> {
                Entity entity = playerLocation.getWorld().spawnEntity(playerLocation, EntityType.BLOCK_DISPLAY);
                if (entity instanceof BlockDisplay blockDisplay) {
                    BlockData blockData = Material.GRASS_BLOCK.createBlockData();
                    blockDisplay.setBlock(blockData);
                    display = blockDisplay;
                }
            }
            case "ItemDisplay" -> {
                Entity entity = playerLocation.getWorld().spawnEntity(playerLocation, EntityType.ITEM_DISPLAY);
                if (entity instanceof ItemDisplay itemDisplay) {
                    ItemStack itemStack = new ItemStack(Material.GRASS);
                    itemDisplay.setItemStack(itemStack);
                    display = itemDisplay;
                }
            }
            default -> sendPrefixedReply(player, Component.text("You have chosen an invalid DisplayEntity Type!",
                                                     NamedTextColor.RED));
        }
        if (display == null) return false;

        selectionCache.setSelection(player, new DisplayEntity(display));
        sendPrefixedReply(player,
                Component.text("Successfully created a new " + args[0] + ".", NamedTextColor.GREEN));
        sendPrefixedReply(player,
                Component.text("Set the new DisplayEntity as selected.", NamedTextColor.GOLD));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return List.of("TextDisplay", "BlockDisplay", "ItemDisplay");
    }
}
