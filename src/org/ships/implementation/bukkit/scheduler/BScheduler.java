package org.ships.implementation.bukkit.scheduler;

import org.bukkit.Bukkit;
import org.core.platform.Plugin;
import org.core.schedule.Scheduler;
import org.core.schedule.SchedulerBuilder;

import java.util.concurrent.TimeUnit;

public class BScheduler implements Scheduler {

    private class RunAfterScheduler implements Runnable {

        @Override
        public void run() {
            BScheduler.this.taskToRun.run();
            Scheduler scheduler = BScheduler.this.runAfter;
            if(scheduler != null){
                scheduler.run();
            }
        }
    }

    protected Runnable taskToRun;
    protected Scheduler runAfter;
    protected int delayCount;
    protected TimeUnit delayTimeUnit;
    protected Plugin plugin;

    public BScheduler(SchedulerBuilder builder, Plugin plugin){
        this.taskToRun = builder.getExecutor();
        this.delayCount = builder.getDelay().orElse(0);
        this.delayTimeUnit = builder.getDelayUnit().orElse(null);
        this.plugin = plugin;
        builder.getToRunAfter().ifPresent(s -> this.runAfter = s);
    }

    @Override
    public void run() {
        long ticks = 0;
        if(this.delayTimeUnit == null) {
            ticks = this.delayCount;
        }else{
            switch (this.delayTimeUnit) {
                case SECONDS:
                    ticks = (this.delayCount / 20);
                default:
                    System.err.println("Unknown TimeUnit: " + this.delayTimeUnit.name());
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((org.bukkit.plugin.Plugin)this.plugin.getBukkitLauncher(), new BScheduler.RunAfterScheduler(), ticks);
    }
}
