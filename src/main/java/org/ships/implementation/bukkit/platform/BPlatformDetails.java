package org.ships.implementation.bukkit.platform;

import org.bukkit.Bukkit;
import org.core.platform.PlatformDetails;

public class BPlatformDetails implements PlatformDetails {
    @Override
    public String getName() {
        return "Bukkit";
    }

    @Override
    public String getIdName() {
        return "bukkit";
    }

    @Override
    public String getVersion() {
        return Bukkit.getBukkitVersion();
    }
}
