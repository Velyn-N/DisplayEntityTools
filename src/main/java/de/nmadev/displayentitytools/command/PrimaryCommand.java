package de.nmadev.displayentitytools.command;

import de.nmadev.displayentitytools.DisplayEntityTools;
import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.SettingCache;
import de.nmadev.displayentitytools.command.subcommands.*;
import de.nmadev.displayentitytools.command.subcommands.plugin.InfoSubCommand;
import de.nmadev.displayentitytools.command.subcommands.plugin.ReloadSubCommand;
import de.nmadev.displayentitytools.command.subcommands.position.GetToolSubCommand;
import de.nmadev.displayentitytools.command.subcommands.text.ChangeTextSubCommand;
import de.nmadev.displayentitytools.command.subcommands.text.LineWidthSubCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PrimaryCommand extends BaseCommand {

    public PrimaryCommand(DisplayEntityTools plugin, SelectionCache selectionCache, SettingCache settingCache, Logger logger) {
        super("displayentitytools", logger, USE_PERMISSION);

        addSubCommand(new InfoSubCommand(plugin, logger));
        addSubCommand(new ReloadSubCommand(plugin, logger));

        addSubCommand(new SelectSubCommand(logger, selectionCache));
        addSubCommand(new DeselectSubCommand(logger, selectionCache));

        addSubCommand(new CreateSubCommand(logger, selectionCache));
        addSubCommand(new DeleteSubCommand(logger, selectionCache));
        addSubCommand(new ChangeTextSubCommand(logger, selectionCache));

        addSubCommand(new LineWidthSubCommand(logger, selectionCache));

        addSubCommand(new GetToolSubCommand(logger, plugin));

        addSubCommand(new SettingSubCommand(logger, settingCache));
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return super.onCommand(sender, commandLabel, args);
    }

    public @NotNull List<String> tabComplete(@NotNull CommandSender sender,
                                             @NotNull String alias,
                                             @NotNull String[] args) {
        return super.onTabComplete(sender, alias, args);
    }

    @Override
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        sendPrefixedReply(sender, Component.text("Please use one of the SubCommands.", NamedTextColor.YELLOW));
        return true;
    }
}
