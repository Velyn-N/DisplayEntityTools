package de.nmadev.displayentitytools;

import de.nmadev.displayentitytools.command.PrimaryCommand;
import de.nmadev.displayentitytools.gui.InventoryClickListener;
import de.nmadev.displayentitytools.position.ItemClickListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class DisplayEntityTools extends JavaPlugin {

    private ConfigReader configReader;
    private Logger logger;
    private SelectionCache selectionCache;
    private SettingCache settingCache;
    private Command commandWrapper;

    @Override
    public void onEnable() {
        updateConfig();

        configReader = new ConfigReader(getConfig());

        logger = new Logger(getLogger(), configReader.isDebugMode());
        logger.debug("Debug Mode is turned on.");

        selectionCache = new SelectionCache();
        settingCache = new SettingCache();

        createCommand(selectionCache, settingCache);

        registerEventListener(new InventoryClickListener());
        registerEventListener(new ItemClickListener(this, selectionCache, settingCache));
    }

    private void createCommand(SelectionCache selectionCache, SettingCache settingCache) {
        List<String> cmdAliases = configReader.getCommandAliases();
        PrimaryCommand primaryCommand = new PrimaryCommand(this, selectionCache, settingCache, logger);
        commandWrapper = new Command("displayentitytools",
                                 "The primary Command for DisplayEntityTools giving access to all its Tools.",
                              "/displayentitytools <subcommand> <args>",
                                           cmdAliases) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                return primaryCommand.execute(sender, commandLabel, args);
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender,
                                                     @NotNull String alias,
                                                     @NotNull String[] args) throws IllegalArgumentException {
                return primaryCommand.tabComplete(sender, alias, args);
            }
        };
        getServer().getCommandMap().register(getName(), commandWrapper);
    }

    @Override
    public void onDisable() {
        logger.info("Shutting down DisplayEntityTools.");
    }

    public void reload() {
        super.reloadConfig();
        configReader.setConfig(getConfig());

        logger.setDebugMode(configReader.isDebugMode());

        commandWrapper.setAliases(configReader.getCommandAliases());
    }

    private void registerEventListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void updateConfig() {
        saveDefaultConfig();

        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
