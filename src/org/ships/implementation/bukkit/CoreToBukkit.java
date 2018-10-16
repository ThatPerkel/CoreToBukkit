package org.ships.implementation.bukkit;

import org.core.CorePlugin;
import org.core.platform.Platform;
import org.core.schedule.SchedulerBuilder;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.scheduler.BSchedulerBuilder;

public class CoreToBukkit extends CorePlugin.CoreImplementation {

    protected BukkitPlatform platform = new BukkitPlatform();

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

    @Override
    public SchedulerBuilder createRawSchedulerBuilder() {
        return new BSchedulerBuilder();
    }
}
