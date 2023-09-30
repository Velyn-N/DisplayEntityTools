package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.DisplayEntity;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class SelectSubCommand extends PlayerOnlyBaseCommand {
    private static final int MAX_TEXT_LENGTH = 25;

    private final SelectionCache selectionCache;

    public SelectSubCommand(Logger logger, SelectionCache selectionCache) {
        super("select", logger, USE_PERMISSION);
        this.selectionCache = selectionCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        double radius = 2;
        if (args.length >= 1) {
            String number = args[0];
            try {
                radius = Double.parseDouble(number);
            } catch (NumberFormatException e) {
                sendPrefixedReply(player,
                        Component.text(String.format("Invalid Number format for %s.", number), NamedTextColor.RED));
                return false;
            }
        }
        Optional<Display> displayEntityOptional = getDisplayEntityInRadius(player, radius);
        if (displayEntityOptional.isPresent()) {
            Display display = displayEntityOptional.get();

            DisplayEntity displayEntity = new DisplayEntity(display);
            selectionCache.setSelection(player, displayEntity);

            if (displayEntity.isTextDisplay()) {
                String rawText = PlainTextComponentSerializer.plainText().serialize(displayEntity.asTextDisplay().text());
                String shortText = rawText.length() > MAX_TEXT_LENGTH ? rawText.substring(0, MAX_TEXT_LENGTH) + "..." : rawText;
                sendPrefixedReply(player,
                        Component.text("Successfully selected the TextDisplay beginning with ", NamedTextColor.GREEN)
                                 .append(Component.text(shortText, NamedTextColor.DARK_GREEN)));
            } else if (displayEntity.isBlockDisplay()) {
                BlockDisplay blockDisplay = displayEntity.asBlockDisplay();
                String material = blockDisplay.getBlock().getMaterial().toString();
                sendPrefixedReply(player,
                        Component.text("Successfully selected the ", NamedTextColor.GREEN)
                                 .append(Component.text(material, NamedTextColor.DARK_GREEN))
                                 .append(Component.text(" BlockDisplay.", NamedTextColor.GREEN)));
            } else if (displayEntity.isItemDisplay()) {
                ItemDisplay blockDisplay = displayEntity.asItemDisplay();
                String material = Objects.requireNonNull(blockDisplay.getItemStack()).getType().toString();
                sendPrefixedReply(player,
                        Component.text("Successfully selected the ", NamedTextColor.GREEN)
                                 .append(Component.text(material, NamedTextColor.DARK_GREEN))
                                 .append(Component.text(" ItemDisplay.", NamedTextColor.GREEN)));
            }

            return true;
        }
        return false;
    }

    private Optional<Display> getDisplayEntityInRadius(Player player, double radius) {
        Location playerLoc = player.getLocation();
        Collection<Entity> nearbyEntities = playerLoc.getWorld().getNearbyEntities(playerLoc, radius, radius, radius);

        List<Display> nearbyTexts = nearbyEntities.stream()
                .filter(entity -> entity instanceof Display)
                .map(entity -> (Display) entity)
                .toList();

        if (nearbyTexts.isEmpty()) {
            sendPrefixedReply(player, Component.text(
                    String.format("Could not find a DisplayEntity in a Range of %.3f Blocks.", radius),
                    NamedTextColor.RED));
            return Optional.empty();
        }
        if (nearbyTexts.size() > 1) {
            sendPrefixedReply(player, Component.text(
                    String.format("There are %d DisplayEntities in a %.3f Block Radius around you.",
                            nearbyTexts.size(),
                            radius), NamedTextColor.YELLOW));
        }
        return Optional.of(nearbyTexts.get(0));
    }

    @Override
    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return IntStream.range(1, 6).mapToObj(String::valueOf).toList();
    }
}
