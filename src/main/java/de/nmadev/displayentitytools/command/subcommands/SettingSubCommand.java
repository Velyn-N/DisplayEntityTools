package de.nmadev.displayentitytools.command.subcommands;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SettingCache;
import de.nmadev.displayentitytools.command.PlayerOnlyBaseCommand;
import de.nmadev.displayentitytools.gui.SettingsGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class SettingSubCommand extends PlayerOnlyBaseCommand {

    private final SettingCache settingCache;

    public SettingSubCommand(Logger logger, SettingCache settingCache) {
        super("settings", logger, USE_PERMISSION);
        this.settingCache = settingCache;
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args) {
        new SettingsGui(settingCache.getSettings(player)).openForPlayer(player);
        sendPrefixedReply(player, Component.text("Opened the Setting-GUI for you.", NamedTextColor.GREEN));
        return true;
    }
}
