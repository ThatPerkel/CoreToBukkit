package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.command.BaseCommandLauncher;
import org.core.platform.PlatformServer;
import org.core.world.WorldExtent;
import org.ships.implementation.bukkit.command.BCommand;
import org.ships.implementation.bukkit.world.BWorldExtent;

import java.util.*;

public class BServer implements PlatformServer {

    protected Set<BaseCommandLauncher> commands = new HashSet<>();

    @Override
    public Set<WorldExtent> getWorlds() {
        Set<WorldExtent> set = new HashSet<>();
        Bukkit.getWorlds().forEach(w -> set.add(new BWorldExtent(w)));
        return set;
    }

    @Override
    public Optional<WorldExtent> getWorldByPlatformSpecific(String name) {
        return this.getWorld(name, true);
    }

    @Override
    public Collection<BaseCommandLauncher> getCommands() {
        return Collections.unmodifiableCollection(this.commands);
    }

    @Override
    public void registerCommands(BaseCommandLauncher... commandLaunchers) {
        for(BaseCommandLauncher command : commandLaunchers){
            JavaPlugin plugin = (JavaPlugin) command.getPlugin().getBukkitLauncher();
            PluginCommand command2 = plugin.getCommand(command.getName());
            BCommand command3 = new BCommand(command);
            command2.setTabCompleter(command3);
            command2.setExecutor(command3);
            this.commands.add(command);
        }
    }
}
