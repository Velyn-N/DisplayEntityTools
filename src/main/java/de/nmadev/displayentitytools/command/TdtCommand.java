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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TdtCommand extends BaseCommand implements CommandExecutor, TabCompleter {
    public static final String COMMAND_NAME = "displayentitytools";

    public TdtCommand(DisplayEntityTools plugin, SelectionCache selectionCache, SettingCache settingCache, Logger logger) {
        super(COMMAND_NAME, logger, USE_PERMISSION);
        addSubCommand(new InfoSubCommand(plugin, logger));
        addSubCommand(new ReloadSubCommand(plugin, logger));

        addSubCommand(new SelectSubCommand(logger, selectionCache));
        addSubCommand(new DeselectSubCommand(logger, selectionCache));

        addSubCommand(new CreateSubCommand(logger, selectionCache));
        addSubCommand(new DeleteSubCommand(logger, selectionCache));
        addSubCommand(new ChangeTextSubCommand(logger, selectionCache));

        addSubCommand(new LineWidthSubCommand(logger, selectionCache));

        addSubCommand(new GetToolSubCommand(logger));

        addSubCommand(new SettingSubCommand(logger, settingCache));
    }

    @Override
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        sendPrefixedReply(sender, Component.text("Please use one of the SubCommands.", NamedTextColor.YELLOW));
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        return onCommand(commandSender, label, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        return onTabComplete(commandSender, label, args);
    }
}
