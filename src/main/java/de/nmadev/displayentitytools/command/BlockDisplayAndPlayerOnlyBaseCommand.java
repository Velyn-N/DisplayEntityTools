package de.nmadev.displayentitytools.command;

import de.nmadev.displayentitytools.DisplayEntity;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;

import java.util.Optional;

public abstract class BlockDisplayAndPlayerOnlyBaseCommand extends PlayerOnlyBaseCommand {

    protected final SelectionCache selectionCache;

    public BlockDisplayAndPlayerOnlyBaseCommand(String commandName,
                                                Logger logger,
                                                String requiredPermission,
                                                SelectionCache selectionCache) {
        super(commandName, logger, requiredPermission);
        this.selectionCache = selectionCache;
    }

    public abstract boolean handleCommandExecution(Player player, String label, String[] args, BlockDisplay textDisplay);

    public boolean handleCommandExecution(Player player, String label, String[] args) {
        Optional<DisplayEntity> displayEntityOptional = selectionCache.getSelectedDisplayEntity(player);
        if (displayEntityOptional.isEmpty()) {
            sendPrefixedReply(player,
                    Component.text("You have no DisplayEntity selected.", NamedTextColor.RED));
            return false;
        }
        Optional<BlockDisplay> blockDisplayOptional = displayEntityOptional.get().asBlockDisplayOptional();

        if (blockDisplayOptional.isEmpty()) {
            sendPrefixedReply(player,
                    Component.text("Your selected DisplayEntity is not a BlockDisplay.", NamedTextColor.RED));
            return false;
        }
        return handleCommandExecution(player, label, args, blockDisplayOptional.get());
    }
}
