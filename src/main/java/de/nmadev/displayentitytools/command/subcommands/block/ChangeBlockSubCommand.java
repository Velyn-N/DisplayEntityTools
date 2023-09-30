package de.nmadev.displayentitytools.command.subcommands.block;

import de.nmadev.displayentitytools.Logger;
import de.nmadev.displayentitytools.SelectionCache;
import de.nmadev.displayentitytools.command.BlockDisplayAndPlayerOnlyBaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChangeBlockSubCommand extends BlockDisplayAndPlayerOnlyBaseCommand {

    public ChangeBlockSubCommand(Logger logger, SelectionCache selectionCache) {
        super("change-block", logger, USE_PERMISSION, selectionCache);
    }

    @Override
    public boolean handleCommandExecution(Player player, String label, String[] args, BlockDisplay textDisplay) {
        if (args.length != 1) {
            sendPrefixedReply(player,
                    Component.text("You have to provide a Material.", NamedTextColor.RED));
            return false;
        }

        try {
            Material material = Material.valueOf(args[0]);
            textDisplay.setBlock(material.createBlockData());
            sendPrefixedReply(player,
                    Component.text("The BlockDisplays Material has been adjusted.", NamedTextColor.GREEN));
            return true;
        } catch (IllegalArgumentException e) {
            sendPrefixedReply(player,
                    Component.text("You have provided an invalid Material.", NamedTextColor.RED));
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        String arg = args.length < 1 ? "" : args[0];
        return Arrays.stream(Material.values())
                .filter(Material::isBlock)
                .map(Material::toString)
                .filter(s -> s.startsWith(arg))
                .sorted(Comparator.comparingInt(s -> s.indexOf(arg)))
                .toList();
    }
}
