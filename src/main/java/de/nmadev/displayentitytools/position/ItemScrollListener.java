package de.nmadev.displayentitytools.position;

import de.nmadev.displayentitytools.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemScrollListener implements Listener {

    private final DisplayEntityTools plugin;
    private final SelectionCache selectionCache;
    private final SettingCache settingCache;

    public ItemScrollListener(DisplayEntityTools plugin, SelectionCache selectionCache, SettingCache settingCache) {
        this.plugin = plugin;
        this.selectionCache = selectionCache;
        this.settingCache = settingCache;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int prevSlot = event.getPreviousSlot();
        ItemStack item = player.getInventory().getItem(prevSlot);

        if (item == null || !player.isSneaking()) {
            return;
        }

        int direction = getScrollDirection(newSlot, prevSlot);
        if (direction == 0) {
            return;
        }

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

                PlayerSettings playerSettings = settingCache.getSettings(player);
                PositionHelper.modifyPosition(modTypeOptional.get(),
                                              playerSettings.getModByModType(modTypeOptional.get()) * direction,
                                              textDisplayOpt.get());
            }
        }
    }

    /**
     * Determine scroll direction
     * @param newSlot slot that was scrolled to
     * @param prevSlot slot that was scrolled away from
     * @return 1 for up, -1 for down, 0 for other (e.g., switching via number keys)
     */
    private static int getScrollDirection(int newSlot, int prevSlot) {
        int direction;
        if (newSlot == 0 && prevSlot == 8) {
            direction = 1;
        } else if (newSlot == 8 && prevSlot == 0) {
            direction = -1;
        } else {
            int difference = newSlot - prevSlot;
            if (difference > 1 || difference < -1) {
                direction = 0;
            } else {
                direction = Integer.compare(newSlot, prevSlot);
            }
        }
        return direction;
    }
}
