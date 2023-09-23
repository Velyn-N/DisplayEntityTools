package de.nmadev.displayentitytools.command.subcommands.text;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import java.util.Optional;

public class ChangeTextSubCommand extends PlayerOnlyBaseCommand {

    private final SelectionCache selectionCache;

    public ChangeTextSubCommand(Logger logger, SelectionCache selectionCache) {
        super("change-text", logger, USE_PERMISSION);
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
        TextDisplay textDisplay = textDisplayOptional.get();
        String text = String.join(" ", args);
        textDisplay.text(Component.text(text));
        sendPrefixedReply(player,
                Component.text("The TextDisplays Text has been adjusted.", NamedTextColor.GREEN));
        return true;
    }
}
