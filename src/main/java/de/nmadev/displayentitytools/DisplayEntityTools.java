package de.nmadev.displayentitytools;

import de.nmadev.displayentitytools.command.PrimaryCommand;
import de.nmadev.displayentitytools.gui.InventoryClickListener;
import de.nmadev.displayentitytools.position.ItemClickListener;
import de.nmadev.displayentitytools.position.ItemScrollListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisplayEntityTools extends JavaPlugin {

    private Logger logger;
    private SelectionCache selectionCache;
    private SettingCache settingCache;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createLogger();

        selectionCache = new SelectionCache();
        settingCache = new SettingCache();

        createCommand(selectionCache, settingCache);

        registerEventListener(new InventoryClickListener());
        registerEventListener(new ItemScrollListener(this, selectionCache, settingCache));
        registerEventListener(new ItemClickListener(this, selectionCache, settingCache));
    }

    private void createCommand(SelectionCache selectionCache, SettingCache settingCache) {
        PluginCommand tdtCommand = getCommand(PrimaryCommand.COMMAND_NAME);
        if (tdtCommand == null) {
            logger.error("Could not register TDT-Command! This is a Bug, please report it to the Developer.");
        } else {
            PrimaryCommand commandImpl = new PrimaryCommand(this, selectionCache, settingCache, logger);
            tdtCommand.setExecutor(commandImpl);
            tdtCommand.setTabCompleter(commandImpl);
            logger.info("Successfully registered TDT-Command.");
        }
    }

    private void createLogger() {
        boolean isDebugMode = getConfig().getBoolean("debug");
        logger = new Logger(getLogger(), isDebugMode);
        logger.debug("Debug Mode is turned on.");
    }

    @Override
    public void onDisable() {
        logger.info("Shutting down DisplayEntityTools.");
    }

    public void reload() {
        reloadConfig();
        createLogger();
        selectionCache.clearSelectionMap();
        settingCache.clearSettingsMap();
    }

    private void registerEventListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
