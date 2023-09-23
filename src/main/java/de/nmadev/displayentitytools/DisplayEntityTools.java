package de.nmadev.displayentitytools;

import de.nmadev.displayentitytools.command.TdtCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisplayEntityTools extends JavaPlugin {

    private Logger logger;
    private SelectionCache selectionCache;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createLogger();

        selectionCache = new SelectionCache();

        createCommand(selectionCache);
    }

    private void createCommand(SelectionCache selectionCache) {
        PluginCommand tdtCommand = getCommand(TdtCommand.COMMAND_NAME);
        if (tdtCommand == null) {
            logger.error("Could not register TDT-Command! This is a Bug, please report it to the Developer.");
        } else {
            TdtCommand commandImpl = new TdtCommand(this, selectionCache, logger);
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
    }
}
