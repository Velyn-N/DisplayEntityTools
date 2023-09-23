package de.nmadev.displayentitytools.command.subcommands.plugin;

import de.nmadev.displayentitytools.DisplayEntityTools;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.command.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends BaseCommand {

    private final DisplayEntityTools plugin;

    public ReloadSubCommand(DisplayEntityTools plugin, Logger logger) {
        super("reload", logger, ADMIN_PERMISSION);
        this.plugin = plugin;
    }

    @Override
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        plugin.reload();
        sendPrefixedReply(sender, Component.text("Successfully reloaded DisplayEntityTools.", NamedTextColor.GREEN));
        return true;
    }
}
