package de.nmadev.displayentitytools.command.subcommands.text;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.TextDisplayAndPlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.List;

public class LineWidthSubCommand extends TextDisplayAndPlayerOnlyBaseCommand {

    public LineWidthSubCommand(Logger logger, SelectionCache selectionCache) {
        super("line-width", logger, USE_PERMISSION, selectionCache);
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args, TextDisplay textDisplay) {
        if (args.length == 0) {
            sendPrefixedReply(player,
                    Component.text("This TextDisplay currently has a Line-Width of ", NamedTextColor.GOLD)
                             .append(Component.text(textDisplay.getLineWidth(), NamedTextColor.DARK_GREEN)
                             .append(Component.text(".", NamedTextColor.GOLD))));
            return true;
        }

        int newLineWidth = 200;
        if (!"default".equalsIgnoreCase(args[0])) {
            try {
                newLineWidth = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sendPrefixedReply(player,
                        Component.text(String.format("Invalid Number format for %s.", newLineWidth), NamedTextColor.RED));
                return false;
            }
        }
        int oldLineWidth = textDisplay.getLineWidth();
        textDisplay.setLineWidth(newLineWidth);
        sendPrefixedReply(player,
                Component.text(
                        String.format("Changed the LineWidth from %d to %d.", oldLineWidth, newLineWidth),
                        NamedTextColor.GREEN));
        return true;
    }

    @Override
    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return List.of("default", "50", "100", "200", "300");
    }
}
