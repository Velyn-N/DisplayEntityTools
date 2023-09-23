package de.nmadev.textdisplaytools.command;

import de.nmadev.textdisplaytools.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class BaseCommand {
    public static final String ADMIN_PERMISSION = "textdisplaytools.admin";
    public static final String USE_PERMISSION = "textdisplaytools.use";

    protected final Logger logger;
    protected final String commandName;
    protected final String requiredPermission;
    protected final boolean isPlayerOnly;
    private final List<BaseCommand> subCommands = new ArrayList<>();

    public BaseCommand(String commandName, Logger logger, String requiredPermission) {
        this(commandName, logger, requiredPermission, true);
    }

    public BaseCommand(String commandName, Logger logger, String requiredPermission, boolean isPlayerOnly) {
        this.commandName = commandName;
        this.logger = logger;
        this.requiredPermission = requiredPermission;
        this.isPlayerOnly = isPlayerOnly;
    }

    protected void addSubCommand(BaseCommand subCommand) {
        subCommands.add(subCommand);
    }

    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        return true;
    }

    public boolean handleCommandExecution(Player player, String label, String[] args) {
        return true;
    }

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            for (BaseCommand subCommand : subCommands) {
                if (subCommand.getCommandName().equalsIgnoreCase(args[0])) {
                    return subCommand.onCommand(sender, label, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }
        if (!hasPermission(sender)) {
            sendPrefixedReply(sender,
                    Component.text("You do not have Permission to execute this Command.", NamedTextColor.RED));
            return false;
        }
        if (isPlayerOnly) {
            if (sender instanceof Player player) {
                return handleCommandExecution(player, label, args);
            }
            sendPrefixedReply(sender, Component.text("Only Players can use this Command.", NamedTextColor.RED));
            return false;
        }
        return handleCommandExecution(sender, label, args);
    }

    private boolean hasPermission(CommandSender sender) {
        return requiredPermission == null || requiredPermission.isEmpty() || sender.hasPermission(requiredPermission);
    }

    public List<String> provideTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }

    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        for (BaseCommand subCommand : subCommands) {
            if (subCommand.getCommandName().equalsIgnoreCase(args[0])) {
                return subCommand.onTabComplete(sender, label, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        List<String> tabOptions = provideTabComplete(sender, label, args);
        if (tabOptions == null) {
            tabOptions = new ArrayList<>();
        }
        subCommands.stream()
                .map(BaseCommand::getCommandName)
                .forEach(tabOptions::add);
        if (args.length == 0) {
            return tabOptions.stream()
                             .sorted()
                             .toList();
        }
        return tabOptions.stream()
                         .filter(option -> option.startsWith(args[0]))
                         .sorted(Comparator.comparingInt(o -> o.indexOf(args[0])))
                         .toList();
    }

    protected void sendPrefixedReply(CommandSender sender, Component component) {
        sender.sendMessage(Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text("TextDisplayTools", NamedTextColor.AQUA))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                .append(component));
    }

    public String getCommandName() {
        return commandName;
    }
}
