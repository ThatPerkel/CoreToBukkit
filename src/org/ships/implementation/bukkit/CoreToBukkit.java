package org.ships.implementation.bukkit;

import org.core.CorePlugin;
import org.core.platform.Platform;
import org.ships.implementation.bukkit.platform.BukkitPlatform;

public class CoreToBukkit extends CorePlugin.CoreImplementation {

    BukkitPlatform platform = new BukkitPlatform();

    public CoreToBukkit(){
        init();
    }

    private void init(){
        CoreImplementation.IMPLEMENTATION = this;
    }

    @Override
    public Platform getRawPlatform() {
        return this.platform;
    }
}
