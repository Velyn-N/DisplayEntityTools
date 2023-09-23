package de.nmadev.textdisplaytools.command.subcommands;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.SelectionCache;
import de.nmadev.textdisplaytools.command.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class SelectSubCommand extends BaseCommand {
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
        Optional<TextDisplay> textDisplayOpt = getTextDisplayInRadius(player, radius);
        if (textDisplayOpt.isPresent()) {
            TextDisplay textDisplay = textDisplayOpt.get();

            selectionCache.setSelection(player, textDisplay);

            String rawText = PlainTextComponentSerializer.plainText().serialize(textDisplay.text());
            String shortText = rawText.length() > MAX_TEXT_LENGTH ? rawText.substring(0, MAX_TEXT_LENGTH) + "..." : rawText;
            sendPrefixedReply(player,
                    Component.text("Successfully selected the TextDisplay beginning with ", NamedTextColor.GREEN)
                             .append(Component.text(shortText, NamedTextColor.DARK_GREEN)));
            return true;
        }
        return false;
    }

    private Optional<TextDisplay> getTextDisplayInRadius(Player player, double radius) {
        Location playerLoc = player.getLocation();
        Collection<Entity> nearbyEntities = playerLoc.getWorld().getNearbyEntities(playerLoc, radius, radius, radius);

        List<TextDisplay> nearbyTexts = nearbyEntities.stream()
                .filter(entity -> entity instanceof TextDisplay)
                .map(entity -> (TextDisplay) entity)
                .toList();

        if (nearbyTexts.isEmpty()) {
            sendPrefixedReply(player, Component.text(
                    String.format("Could not find a TextDisplay in a Range of %.3f Blocks.", radius),
                    NamedTextColor.RED));
            return Optional.empty();
        }
        if (nearbyTexts.size() > 1) {
            sendPrefixedReply(player, Component.text(
                    String.format("There are %d TextDisplays in a %.3f Block Radius around you.",
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
