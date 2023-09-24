package de.nmadev.displayentitytools.gui;

import de.nmadev.displayentitytools.PlayerSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SettingsGui extends BaseGui {

    private final PlayerSettings playerSettings;
    private GuiItem xModButton;
    private GuiItem yModButton;
    private GuiItem zModButton;
    private GuiItem rotationModButton;
    private GuiItem tiltModButton;

    public SettingsGui(PlayerSettings playerSettings) {
        super(27, "DisplayEntityTools Settings");
        this.playerSettings = playerSettings;

        createGui();
    }

    private void createGui() {
        xModButton = getModSettingButton(new ItemStack(Material.ARROW),
                playerSettings::getXMod,
                playerSettings::setXMod,
                1,
                0.1);
        yModButton = getModSettingButton(new ItemStack(Material.ARROW),
                playerSettings::getYMod,
                playerSettings::setYMod,
                1,
                0.1);
        zModButton = getModSettingButton(new ItemStack(Material.ARROW),
                playerSettings::getZMod,
                playerSettings::setZMod,
                1,
                0.1);
        rotationModButton = getModSettingButton(new ItemStack(Material.ARROW),
                playerSettings::getRotationMod,
                playerSettings::setRotationMod,
                5,
                1);
        tiltModButton = getModSettingButton(new ItemStack(Material.ARROW),
                playerSettings::getTiltMod,
                playerSettings::setTiltMod,
                1,
                0.1);

        update();
    }

    @Override
    public void update() {
        xModButton.updateMeta(itemMeta -> buildItemMeta(itemMeta,
                                            "X-Axis Modifier",
                                                        playerSettings.getXMod(),
                                        1,
                                          0.1));
        yModButton.updateMeta(itemMeta -> buildItemMeta(itemMeta,
                                            "Y-Axis Modifier",
                                                        playerSettings.getYMod(),
                                        1,
                                          0.1));
        zModButton.updateMeta(itemMeta -> buildItemMeta(itemMeta,
                                            "Z-Axis Modifier",
                                                        playerSettings.getZMod(),
                                        1,
                                          0.1));
        rotationModButton.updateMeta(itemMeta -> buildItemMeta(itemMeta,
                                            "Rotation Modifier",
                                                        playerSettings.getRotationMod(),
                                        5,
                                          1));
        tiltModButton.updateMeta(itemMeta -> buildItemMeta(itemMeta,
                                            "Tilt Modifier",
                                                        playerSettings.getTiltMod(),
                                        1,
                                          0.1));

        setItem(9, xModButton);
        setItem(11, yModButton);
        setItem(13, zModButton);
        setItem(15, rotationModButton);
        setItem(17, tiltModButton);
    }

    private ItemMeta buildItemMeta(ItemMeta itemMeta,
                                   String modifierName,
                                   double modifierValue,
                                   double normalClickModifier,
                                   double shiftClickModifier) {
        itemMeta.displayName(Component.text(modifierName, NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        itemMeta.lore(List.of(
                Component.empty(),
                Component.text("Current Value: ", NamedTextColor.GOLD)
                         .append(Component.text(String.format("%.2f", modifierValue), NamedTextColor.YELLOW)),
                buildGuideText("Left-Click", normalClickModifier, true),
                buildGuideText("Right-Click", normalClickModifier, false),
                buildGuideText("Shift-Left-Click", shiftClickModifier, true),
                buildGuideText("Shift-Right-Click", shiftClickModifier, false)
        ));
        return itemMeta;
    }

    private TextComponent buildGuideText(String clickType, double modifier, boolean increases) {
        return Component.text(clickType, NamedTextColor.YELLOW)
                        .append(Component.text(" to ", NamedTextColor.GOLD))
                        .append(Component.text(increases ? "increase" : "decrease",
                                               increases ? NamedTextColor.GREEN : NamedTextColor.RED))
                        .append(Component.text(" by ", NamedTextColor.GOLD))
                        .append(Component.text(modifier, NamedTextColor.YELLOW));
    }

    private GuiItem getModSettingButton(ItemStack itemStack,
                                        Supplier<Double> getter,
                                        Consumer<Double> setter,
                                        double normalClickModifier,
                                        double shiftClickModifier) {
        GuiItem guiItem = new GuiItem(itemStack);

        guiItem.setAction(ClickType.LEFT, () -> setter.accept(getter.get() + normalClickModifier));
        guiItem.setAction(ClickType.SHIFT_LEFT, () -> setter.accept(getter.get() + shiftClickModifier));
        guiItem.setAction(ClickType.RIGHT, () -> setter.accept(getter.get() - normalClickModifier));
        guiItem.setAction(ClickType.SHIFT_RIGHT, () -> setter.accept(getter.get() - shiftClickModifier));

        return guiItem;
    }
}
