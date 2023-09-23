package de.nmadev.textdisplaytools.command.subcommands;

import de.nmadev.textdisplaytools.Logger;
import de.nmadev.textdisplaytools.TextDisplayTools;
import de.nmadev.textdisplaytools.command.BaseCommand;
import io.papermc.paper.plugin.configuration.PluginMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Objects;

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

        Component description = Component.text(Objects.requireNonNull(pluginMeta.getDescription()), NamedTextColor.YELLOW);

        Component version = getLabeledValue("Version", pluginMeta.getVersion());
        Component authors = getLabeledValue("Authors", String.join(", ", pluginMeta.getAuthors()));
        Component contributors = getLabeledValue("Contributors", String.join(", ", pluginMeta.getContributors()));
        Component website = createWebsiteComponent(pluginMeta.getWebsite());

        Component footer = Component.text("--- --- --- --- --- --- --- --- ---", NamedTextColor.GOLD)
                                    .decorate(TextDecoration.BOLD);

        TextComponent result = Component.empty()
                .append(headline)
                .append(Component.newline())
                .append(description)
                .append(Component.newline())
                .append(version)
                .append(Component.newline())
                .append(authors)
                .append(Component.newline())
                .append(contributors)
                .append(Component.newline())
                .append(website)
                .append(Component.newline())
                .append(Component.newline())
                .append(createEnvInfoComponent(pluginMeta.getName(), pluginMeta.getVersion()))
                .append(Component.newline())
                .append(footer);

        sender.sendMessage(result);
        return true;
    }

    private Component getLabeledValue(String label, String value) {
        return Component.text(label + ": ", NamedTextColor.GOLD)
                        .append(Component.text(value, NamedTextColor.YELLOW));
    }

    private Component createWebsiteComponent(String website) {
        return Component.text( "Website: ", NamedTextColor.GOLD)
                .append(Component.text(website, NamedTextColor.YELLOW).decorate(TextDecoration.UNDERLINED))
                .clickEvent(ClickEvent.openUrl(Objects.requireNonNull(website)))
                .hoverEvent(HoverEvent.showText(Component.text("Click to open Link")));
    }

    private Component createEnvInfoComponent(String pluginName, String pluginVersion) {
        String serverSoftware = Bukkit.getName();
        String serverVersion = Bukkit.getVersion();
        String jdkVersion = System.getProperty("java.version");

        String fullInfo = String.format("%s Version: %s%nServer Software: %s%nServer Version: %s%nJDK Version: %s",
                pluginName, pluginVersion, serverSoftware, serverVersion, jdkVersion);

        return Component.text("Copy Environment Information", NamedTextColor.YELLOW)
                .decorate(TextDecoration.UNDERLINED)
                .hoverEvent(HoverEvent.showText(Component.text("Click to copy Environment Information to Clipboard")))
                .clickEvent(ClickEvent.copyToClipboard(fullInfo));
    }
}
