package org.ships.implementation.bukkit.platform;

import org.core.platform.Plugin;

import java.util.Optional;

public class BPlugin implements Plugin {

    org.bukkit.plugin.Plugin plugin;

    public BPlugin(org.bukkit.plugin.Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getPluginName() {
        return this.plugin.getName();
    }

    @Override
    public String getPluginId() {
        return this.plugin.getName().toLowerCase();
    }

    @Override
    public String getPluginVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public Optional<Object> getBukkitLauncher() {
        return Optional.of(this.plugin);
    }

    @Override
    public Optional<Object> getSpongeLauncher() {
        return Optional.empty();
    }
}
