package org.ships.implementation.bukkit.platform;

import org.core.platform.Plugin;

public class BPlugin implements Plugin {

    protected org.bukkit.plugin.Plugin plugin;

    public BPlugin(org.bukkit.plugin.Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getPluginName() {
        return this.plugin.getName();
    }

    @Override
    public String getPluginId() {
        return this.plugin.getName().toLowerCase().replaceAll(" ", "_");
    }

    @Override
    public String getPluginVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public org.bukkit.plugin.Plugin getLauncher() {
        return this.plugin;
    }

}
