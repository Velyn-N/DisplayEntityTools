package de.nmadev.displayentitytools.position;

import de.nmadev.displayentitytools.DisplayEntityTools;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public final class PositionHelper {

    private PositionHelper() {}

    public static NamespacedKey getPositionModTypeKey(DisplayEntityTools plugin) {
        return new NamespacedKey(plugin, "PositionModificationType");
    }

    public static void modifyPosition(PositionModificationType type, double value, Display display) {
        switch (type) {
            case X_AXIS -> moveEntity(display, value, 0, 0, 0, 0);
            case Y_AXIS -> moveEntity(display, 0, value, 0, 0, 0);
            case Z_AXIS -> moveEntity(display, 0, 0, value, 0, 0);
            case ROTATION -> moveEntity(display, 0, 0, 0, value, 0);
            case TILT -> moveEntity(display, 0, 0, 0, 0, value);
        }
    }

    private static void moveEntity(Display entity, double xMod, double yMod, double zMod, double rotMod, double tiltMod) {
        Location currentLocation = entity.getLocation();

        float newYaw = (float)((currentLocation.getYaw() + rotMod) % 360);
        float newPitch = (float)((currentLocation.getPitch() + tiltMod) % 360);

        Location newLocation = new Location(
                currentLocation.getWorld(),
                currentLocation.getX() + xMod,
                currentLocation.getY() + yMod,
                currentLocation.getZ() + zMod,
                newYaw,
                newPitch);

        entity.teleport(newLocation);
    }

    public static Optional<PositionModificationType> getPositionModType(ItemMeta meta, DisplayEntityTools plugin) {
        try {
            Integer ordinal = meta.getPersistentDataContainer()
                    .get(PositionHelper.getPositionModTypeKey(plugin), PersistentDataType.INTEGER);
            if (ordinal != null) {
                return Optional.ofNullable(PositionModificationType.values()[ordinal]);
            }
        } catch (IndexOutOfBoundsException ignored) {}
        return Optional.empty();
    }
}
