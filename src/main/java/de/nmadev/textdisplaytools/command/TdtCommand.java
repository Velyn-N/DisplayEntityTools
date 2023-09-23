package de.nmadev.textdisplaytools.command;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.SelectionCache;
import de.nmadev.textdisplaytools.TextDisplayTools;
import de.nmadev.textdisplaytools.command.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TdtCommand extends BaseCommand implements CommandExecutor, TabCompleter {
    public static final String COMMAND_NAME = "textdisplaytools";

    public TdtCommand(TextDisplayTools plugin, SelectionCache selectionCache, Logger logger) {
        super(COMMAND_NAME, logger, USE_PERMISSION);
        addSubCommand(new InfoSubCommand(plugin, logger));
        addSubCommand(new ReloadSubCommand(plugin, logger));

        addSubCommand(new SelectSubCommand(logger, selectionCache));
        addSubCommand(new DeselectSubCommand(logger, selectionCache));

        addSubCommand(new CreateSubCommand(logger, selectionCache));
        addSubCommand(new ChangeTextSubCommand(logger, selectionCache));

        addSubCommand(new LineWidthSubCommand(logger, selectionCache));
    }

    @Override
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
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
