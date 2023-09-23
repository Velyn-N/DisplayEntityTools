package de.nmadev.displayentitytools.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemScrollListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int prevSlot = event.getPreviousSlot();
        ItemStack item = player.getInventory().getItem(prevSlot);

        if (item == null || !player.isSneaking()) return;

        // Determine scroll direction
        int direction;  // 1 for up, -1 for down, 0 for other (e.g., switching via number keys)
        if (newSlot == 0 && prevSlot == 8) {
            direction = 1;
        } else if (newSlot == 8 && prevSlot == 0) {
            direction = -1;
        } else {
            direction = Integer.compare(newSlot, prevSlot);
        }

        if (item.getType() == Material.STICK) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && isTdtItem(meta)) {
                // Cancel the event to keep the item in hand
                event.setCancelled(true);

                // Your logic based on scroll direction
                if (direction == 1) {
                    player.sendMessage("You scrolled up while sneaking with a special sword!");
                } else if (direction == -1) {
                    player.sendMessage("You scrolled down while sneaking with a special sword!");
                }
            }
        }
    }

    private boolean isTdtItem(ItemMeta meta) {
        List<Component> loreLines = meta.lore();
        return loreLines != null && loreLines.stream()
                .anyMatch(comp -> comp.contains(Component.text("Da Stick")));
    }
}
