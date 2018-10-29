package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.core.platform.PlatformServer;
import org.core.world.WorldExtent;
import org.ships.implementation.bukkit.world.BWorldExtent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BServer implements PlatformServer {
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
}
