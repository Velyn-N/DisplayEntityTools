package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

public class CreateSubCommand extends PlayerOnlyBaseCommand {

    private final SelectionCache selectionCache;

    public CreateSubCommand(Logger logger, SelectionCache selectionCache) {
        super("create", logger, USE_PERMISSION);
        this.selectionCache = selectionCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        Location playerLocation = player.getLocation();
        Entity entity = playerLocation.getWorld().spawnEntity(playerLocation, EntityType.TEXT_DISPLAY);
        if (entity instanceof TextDisplay textDisplay) {
            textDisplay.text(Component.text("Your new TextDisplay"));
            sendPrefixedReply(player,
                    Component.text("Successfully created a new TextDisplay.", NamedTextColor.GREEN));
            selectionCache.setSelection(player, textDisplay);
            sendPrefixedReply(player,
                    Component.text("Set the new TextDisplay as selected.", NamedTextColor.GOLD));
        }
        return true;
    }
}
