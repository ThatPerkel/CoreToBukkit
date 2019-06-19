package org.ships.implementation.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.CorePlugin;
import org.core.configuration.ConfigurationFile;
import org.core.configuration.type.ConfigurationLoaderType;
import org.core.configuration.type.ConfigurationLoaderTypes;
import org.core.event.EventManager;
import org.core.platform.Platform;
import org.core.platform.PlatformServer;
import org.core.schedule.SchedulerBuilder;
import org.core.source.command.ConsoleSource;
import org.core.text.Text;
import org.ships.implementation.bukkit.configuration.YAMLConfigurationFile;
import org.ships.implementation.bukkit.event.BEventManager;
import org.ships.implementation.bukkit.event.BukkitListener;
import org.ships.implementation.bukkit.platform.BServer;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.platform.PlatformConsole;
import org.ships.implementation.bukkit.scheduler.BSchedulerBuilder;
import org.ships.implementation.bukkit.text.BText;

import java.io.File;
import java.io.IOException;

public class CoreToBukkit extends CorePlugin.CoreImplementation {

    protected BukkitPlatform platform = new BukkitPlatform();
    protected BEventManager manager = new BEventManager();
    protected BServer server = new BServer();
    protected PlatformConsole console = new PlatformConsole();


    public CoreToBukkit(JavaPlugin plugin){
        init(plugin);
    }

    private void init(JavaPlugin plugin){
        CoreImplementation.IMPLEMENTATION = this;
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), plugin);
        this.platform.init();
    }

    @Override
    public Platform getRawPlatform() {
        return this.platform;
    }

    @Override
    public EventManager getRawEventManager() {
        return this.manager;
    }

    @Override
    public ConsoleSource getRawConsole() {
        return this.console;
    }

    @Override
    public SchedulerBuilder createRawSchedulerBuilder() {
        return new BSchedulerBuilder();
    }

    @Override
    public ConfigurationFile createRawConfigurationFile(File file, ConfigurationLoaderType type) {
        if(file == null){
            System.err.println("File can not be null");
            new IOException("Unknown file").printStackTrace();
            return null;
        }
        if(type == null){
            System.err.println("ConfigurationLoaderType can not be null");
            new IOException("Unknown Configuration Loader Type").printStackTrace();
            return null;
        }
        if(type.equals(ConfigurationLoaderTypes.YAML) || type.equals(ConfigurationLoaderTypes.DEFAULT)){
            File file2 = file;
            if(file.getName().endsWith(".temp")){
                file2 = new File(file.getParentFile(), file.getName().substring(0, file.getName().length() - 4) + "yml");
            }
            /*if(!file2.exists()){
                try {
                    file2.getParentFile().mkdirs();
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            return new YAMLConfigurationFile(file2);
        }
        System.err.println("ConfigurationLoaderType is not supported: " + type.getId());
        return null;
    }

    @Override
    public PlatformServer getRawServer() {
        return this.server;
    }

    @Override
    public Text textBuilder(String chars) {
        return new BText(chars);
    }
}
