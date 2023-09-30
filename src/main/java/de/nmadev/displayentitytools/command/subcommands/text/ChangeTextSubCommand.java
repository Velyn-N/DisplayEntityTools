package de.nmadev.displayentitytools.command.subcommands.text;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.TextEntityAndPlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class ChangeTextSubCommand extends TextEntityAndPlayerOnlyBaseCommand {

    public ChangeTextSubCommand(Logger logger, SelectionCache selectionCache) {
        super("change-text", logger, USE_PERMISSION, selectionCache);
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args, TextDisplay textDisplay) {
        String text = String.join(" ", args);
        textDisplay.text(Component.text(text));
        sendPrefixedReply(player,
                Component.text("The TextDisplays Text has been adjusted.", NamedTextColor.GREEN));
        return true;
    }
}
