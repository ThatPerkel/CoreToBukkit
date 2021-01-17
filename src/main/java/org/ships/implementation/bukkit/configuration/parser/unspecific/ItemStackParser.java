package org.ships.implementation.bukkit.configuration.parser.unspecific;

import org.array.utils.ArrayUtils;
import org.core.config.ConfigurationNode;
import org.core.config.ConfigurationStream;
import org.core.config.parser.unspecific.UnspecificParser;
import org.core.inventory.item.stack.ItemStack;
import org.ships.implementation.bukkit.configuration.YAMLConfigurationFile;
import org.ships.implementation.bukkit.inventory.item.stack.BAbstractItemStack;

import java.util.Optional;

public class ItemStackParser implements UnspecificParser<ItemStack> {

    @Override
    public void set(ConfigurationStream file, ItemStack value, ConfigurationNode node) {
        ((YAMLConfigurationFile) file).getYaml().set(ArrayUtils.toString(".", t -> t, node.getPath()), ((BAbstractItemStack)value).getBukkitItem());
    }

    @Override
    public Optional<ItemStack> parse(ConfigurationStream file, ConfigurationNode node) {
        ((YAMLConfigurationFile) file).getYaml().getItemStack(ArrayUtils.toString(".", t -> t, node.getPath()));
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
