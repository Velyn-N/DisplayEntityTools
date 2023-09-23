package de.nmadev.textdisplaytools.command.subcommands;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.SelectionCache;
import de.nmadev.textdisplaytools.command.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class DeselectSubCommand extends BaseCommand {

    private final SelectionCache selectionCache;

    public DeselectSubCommand(Logger logger, SelectionCache selectionCache) {
        super("deselect", logger, USE_PERMISSION);
        this.selectionCache = selectionCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        if (selectionCache.deselectTextDisplay(player).isPresent()) {
            sendPrefixedReply(player,
                    Component.text("Successfully deselected the TextDisplay.", NamedTextColor.GREEN));
        } else {
            sendPrefixedReply(player,
                    Component.text("You had no TextDisplay selected.", NamedTextColor.GREEN));
        }
        return true;
    }
}
