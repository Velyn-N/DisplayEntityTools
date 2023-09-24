package de.nmadev.displayentitytools.position;

import de.nmadev.displayentitytools.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemClickListener implements Listener {

    private final DisplayEntityTools plugin;
    private final SelectionCache selectionCache;
    private final SettingCache settingCache;

    public ItemClickListener(DisplayEntityTools plugin, SelectionCache selectionCache, SettingCache settingCache) {
        this.plugin = plugin;
        this.selectionCache = selectionCache;
        this.settingCache = settingCache;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.STICK) {
            Optional<PositionModificationType> modTypeOptional = PositionHelper.getPositionModType(item.getItemMeta(),
                    plugin);
            if (modTypeOptional.isPresent()) {
                event.setCancelled(true);

                Optional<TextDisplay> textDisplayOpt = selectionCache.getSelectedTextDisplay(player);
                if (textDisplayOpt.isEmpty()) {
                    MessageFormatHelper.sendPrefixedMessage(player,
                            Component.text("You have no DisplayEntity selected.", NamedTextColor.RED));
                    return;
                }

                int direction = event.getAction().isLeftClick() ? 1 : -1;

                PlayerSettings playerSettings = settingCache.getSettings(player);
                PositionHelper.modifyPosition(modTypeOptional.get(),
                        playerSettings.getModByModType(modTypeOptional.get()) * direction,
                        textDisplayOpt.get());
            }
        }
    }
}
