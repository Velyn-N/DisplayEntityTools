package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class DeselectSubCommand extends PlayerOnlyBaseCommand {

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
