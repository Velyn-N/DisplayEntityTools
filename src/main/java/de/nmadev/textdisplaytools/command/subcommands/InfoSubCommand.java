package de.nmadev.textdisplaytools.command.subcommands;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.TextDisplayTools;
import de.nmadev.textdisplaytools.command.BaseCommand;
import io.papermc.paper.plugin.configuration.PluginMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public class InfoSubCommand extends BaseCommand {

    private final TextDisplayTools plugin;

    public InfoSubCommand(TextDisplayTools plugin, Logger logger) {
        super("info", logger, USE_PERMISSION, false);
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public boolean handleCommandExecution(CommandSender sender, String label, String[] args) {
        PluginMeta pluginMeta = plugin.getPluginMeta();

        String headlineText = "--- " + pluginMeta.getName() + " Plugin Info ---";
        TextComponent headline = Component.text(headlineText, NamedTextColor.GOLD).decorate(TextDecoration.BOLD);

        String pluginDesc = pluginMeta.getDescription();
        Component description = Component.text(pluginDesc == null ? "" : pluginDesc, NamedTextColor.YELLOW);

        Component version = getLabeledValue("Version", pluginMeta.getVersion());
        Component authors = getLabeledValue("Authors", String.join(", ", pluginMeta.getAuthors()));
        Component contributors = getLabeledValue("Contributors", String.join(", ", pluginMeta.getContributors()));
        Component website = getLabeledValue("Website", pluginMeta.getWebsite());

        TextComponent result = Component.empty().append(headline).append(Component.newline());
        if (pluginDesc != null) {
            result = result.append(description).append(Component.newline());
        }
        result = result
                    .append(version).append(Component.newline())
                    .append(authors).append(Component.newline())
                    .append(contributors).append(Component.newline())
                    .append(website).append(Component.newline());

        sender.sendMessage(result);
        return true;
    }

    private Component getLabeledValue(String label, String value) {
        return Component.text(label + ": ", NamedTextColor.GOLD)
                        .append(Component.text(value, NamedTextColor.YELLOW));
    }
}
