package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.core.CorePlugin;
import org.core.command.CommandLauncher;
import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.User;
import org.core.platform.PlatformServer;
import org.core.platform.tps.TPSExecutor;
import org.core.world.WorldExtent;
import org.ships.implementation.bukkit.command.BCommand;
import org.ships.implementation.bukkit.entity.living.human.player.live.BUser;
import org.ships.implementation.bukkit.world.BWorldExtent;

import java.util.*;

public class BServer implements PlatformServer {

    protected Set<CommandLauncher> commands = new HashSet<>();
    protected TPSExecutor tpsExecutor = new TPSExecutor();

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
    public Collection<LivePlayer> getOnlinePlayers() {
        Set<LivePlayer> set = new HashSet<>();
        BukkitPlatform platform = ((BukkitPlatform)CorePlugin.getPlatform());
        Bukkit.getServer().getOnlinePlayers().forEach(p -> set.add((LivePlayer) platform.createEntityInstance(p)));
        return set;
    }

    @Override
    public Optional<User> getOfflineUser(UUID uuid) {
        Player player = Bukkit.getServer().getPlayer(uuid);
        if(player != null){
            return Optional.of((LivePlayer)((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(player));
        }
        OfflinePlayer user = Bukkit.getServer().getOfflinePlayer(uuid);
        if(user == null){
            return Optional.empty();
        }
        return Optional.of(new BUser(user));
    }

    @Override
    public TPSExecutor getTPSExecutor() {
        return this.tpsExecutor;
    }

    @Override
    public Collection<CommandLauncher> getCommands() {
        return Collections.unmodifiableCollection(this.commands);
    }

    @Override
    public void registerCommands(CommandLauncher... commandLaunchers) {
        for(CommandLauncher command : commandLaunchers){
            JavaPlugin plugin = (JavaPlugin) command.getPlugin().getLauncher();
            PluginCommand command2 = plugin.getCommand(command.getName());
            BCommand command3 = new BCommand(command);
            command2.setTabCompleter(command3);
            command2.setExecutor(command3);
            this.commands.add(command);
        }
    }
}
