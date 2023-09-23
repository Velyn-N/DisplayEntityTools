package de.nmadev.displayentitytools.command;

import de.nmadev.displayentitytools.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerOnlyBaseCommand extends BaseCommand {

    public PlayerOnlyBaseCommand(String commandName, Logger logger, String requiredPermission) {
        super(commandName, logger, requiredPermission);
    }

    public abstract boolean handleCommandExecution(Player player, String label, String[] args);

    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player player) {
            return handleCommandExecution(player, label, args);
        }
        sendPrefixedReply(sender, Component.text("Only Players can use this Command.", NamedTextColor.RED));
        return false;
    }
}
