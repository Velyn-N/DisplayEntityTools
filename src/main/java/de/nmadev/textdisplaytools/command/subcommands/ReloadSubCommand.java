package de.nmadev.textdisplaytools.command.subcommands;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.TextDisplayTools;
import de.nmadev.textdisplaytools.command.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends BaseCommand {

    private final TextDisplayTools plugin;

    public ReloadSubCommand(TextDisplayTools plugin, Logger logger) {
        super("reload", logger, ADMIN_PERMISSION, false);
        this.plugin = plugin;
    }

    @Override
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        plugin.reload();
        sendPrefixedReply(sender, Component.text("Successfully reloaded TextDisplayTools.", NamedTextColor.GREEN));
        return true;
    }
}
