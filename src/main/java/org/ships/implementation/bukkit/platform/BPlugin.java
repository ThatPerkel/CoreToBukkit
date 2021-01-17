package org.ships.implementation.bukkit.platform;

import org.core.command.CommandRegister;
import org.core.config.ConfigurationStream;
import org.core.platform.Plugin;
import org.ships.implementation.bukkit.configuration.YAMLConfigurationFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

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
    @Deprecated
    public void registerCommands(CommandRegister register) {

    }

    @Override
    public org.bukkit.plugin.Plugin getLauncher() {
        return this.plugin;
    }

    @Override
    public Optional<ConfigurationStream.ConfigurationFile> createConfig(String configName, File file) {
        InputStream stream = this.plugin.getResource(configName);
        if(stream == null){
            System.err.println("Request for '" + configName + "' could not be found");
            return Optional.empty();
        }
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            Files.copy(stream, file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(new YAMLConfigurationFile(file));
    }

}
