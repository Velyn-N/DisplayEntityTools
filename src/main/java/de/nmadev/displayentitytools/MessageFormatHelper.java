package de.nmadev.displayentitytools;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public final class MessageFormatHelper {

    private MessageFormatHelper() {}

    public static void sendPrefixedMessage(Player player, Component component) {
        player.sendMessage(prefixComponent(component));
    }

    public static Component prefixComponent(Component component) {
        return Component.text("[", NamedTextColor.DARK_GRAY)
                        .append(Component.text("DisplayEntityTools", NamedTextColor.AQUA))
                        .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                        .append(component);
    }
}
