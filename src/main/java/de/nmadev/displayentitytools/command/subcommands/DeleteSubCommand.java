package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.Optional;

public class DeleteSubCommand extends PlayerOnlyBaseCommand {

    private final SelectionCache selectionCache;

    public DeleteSubCommand(Logger logger, SelectionCache selectionCache) {
        super("delete", logger, USE_PERMISSION);
        this.selectionCache = selectionCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        Optional<TextDisplay> textDisplayOptional = selectionCache.getSelectedTextDisplay(player);
        if (textDisplayOptional.isEmpty()) {
            sendPrefixedReply(player,
                    Component.text("You have no TextDisplay selected.", NamedTextColor.RED));
            return false;
        }
        textDisplayOptional.get().remove();
        sendPrefixedReply(player,
                Component.text("The TextDisplay has been deleted.", NamedTextColor.GREEN));
        return true;
    }
}
