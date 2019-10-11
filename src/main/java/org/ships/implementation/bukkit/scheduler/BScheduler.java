package org.ships.implementation.bukkit.scheduler;

import org.bukkit.Bukkit;
import org.core.platform.Plugin;
import org.core.schedule.Scheduler;
import org.core.schedule.SchedulerBuilder;

import java.io.IOException;
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
    protected Integer iteration;
    protected TimeUnit iterationTimeUnit;
    protected String displayName;
    protected Plugin plugin;

    protected int task;

    public BScheduler(SchedulerBuilder builder, Plugin plugin){
        this.taskToRun = builder.getExecutor();
        this.iteration = builder.getIteration().orElse(null);
        this.iterationTimeUnit = builder.getIterationUnit().orElse(null);
        this.delayCount = builder.getDelay().orElse(0);
        this.delayTimeUnit = builder.getDelayUnit().orElse(null);
        this.plugin = plugin;
        this.displayName = builder.getDisplayName().orElse(null);
        if(this.displayName == null){
            System.err.println("No Display Name");
            new IOException("No DisplayName").printStackTrace();
        }
        builder.getToRunAfter().ifPresent(s -> this.runAfter = s);
    }

    @Override
    public void run() {
        long ticks = 0;
        if(this.delayTimeUnit == null) {
            ticks = this.delayCount;
        }else{
            switch (this.delayTimeUnit) {
                case MILLISECONDS:
                    ticks = ((this.delayCount * 20)/1000); break;
                case MICROSECONDS:
                    ticks = ((this.delayCount * 20)/1000000); break;
                case SECONDS:
                    ticks = (this.delayCount * 20); break;
                case MINUTES:
                    ticks = ((this.delayCount * 20)*100); break;
                default:
                    System.err.println("Unknown TimeUnit: " + this.delayTimeUnit.name());
            }
        }
        Integer iter = null;
        if(this.iteration != null) {
            if (this.iterationTimeUnit == null) {
                iter = this.iteration;
            } else {
                switch (this.iterationTimeUnit) {
                    case MILLISECONDS:
                        iter = ((this.iteration * 20)/1000); break;
                    case MICROSECONDS:
                        iter = ((this.iteration * 20)/1000000); break;
                    case SECONDS:
                        iter = (this.iteration * 20); break;
                    case MINUTES:
                        iter = ((this.iteration * 20)*100); break;
                    default:
                        System.err.println("Unknown TimeUnit: " + this.iterationTimeUnit.name());
                }
            }
        }
        if(iter == null){
            this.task = Bukkit.getScheduler().scheduleSyncDelayedTask((org.bukkit.plugin.Plugin) this.plugin.getLauncher(), new BScheduler.RunAfterScheduler(), ticks);
        } else {
            this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask((org.bukkit.plugin.Plugin) this.plugin.getLauncher(), new BScheduler.RunAfterScheduler(), ticks, iter);
        }
    }

    @Override
    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.task);
    }

    @Override
    public Runnable getExecutor() {
        return this.taskToRun;
    }

    @Override
    public String toString(){
        String str = this.displayName + ": Delay(Time: " + this.delayCount + " Unit: " + this.delayTimeUnit + ") Iteration: (Time: " + this.iteration + " Unit: " + this.iterationTimeUnit + ") Plugin: " + this.plugin.getPluginId() + " ID:" + this.task;
        if(this.runAfter == null){
            return str + " ToRunAfter: None";
        }else if(this.runAfter instanceof BScheduler){
            return str + " ToRunAfter: " + ((BScheduler)this.runAfter).task;
        }
        return str + " ToRunAfter: Unknown";

    }
}
