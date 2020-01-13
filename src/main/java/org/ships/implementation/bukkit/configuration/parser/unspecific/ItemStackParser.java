package org.ships.implementation.bukkit.configuration.parser.unspecific;

import org.core.CorePlugin;
import org.core.configuration.ConfigurationFile;
import org.core.configuration.ConfigurationNode;
import org.core.configuration.parser.unspecific.UnspecificParser;
import org.core.inventory.item.stack.ItemStack;
import org.ships.implementation.bukkit.configuration.YAMLConfigurationFile;
import org.ships.implementation.bukkit.inventory.item.stack.BAbstractItemStack;

import java.util.Optional;

public class ItemStackParser implements UnspecificParser<ItemStack> {

    @Override
    public void set(ConfigurationFile file, ItemStack value, ConfigurationNode node) {
        ((YAMLConfigurationFile) file).getYaml().set(CorePlugin.toString(".", node.getPath()), ((BAbstractItemStack)value).getBukkitItem());
    }

    @Override
    public Optional<ItemStack> parse(ConfigurationFile file, ConfigurationNode node) {
        ((YAMLConfigurationFile) file).getYaml().getItemStack(CorePlugin.toString(".", node.getPath()));
        return Optional.empty();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
