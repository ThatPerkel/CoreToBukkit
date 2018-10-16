package org.ships.implementation.bukkit.scheduler;

import org.core.platform.Plugin;
import org.core.schedule.Scheduler;
import org.core.schedule.SchedulerBuilder;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BSchedulerBuilder implements SchedulerBuilder {

    Integer delay;
    TimeUnit delayUnit;
    Runnable executor;
    Scheduler runAfter;

    @Override
    public Optional<Integer> getDelay() {
        return Optional.empty();
    }

    @Override
    public SchedulerBuilder setDelay(Integer value) {
        return null;
    }

    @Override
    public Optional<TimeUnit> getDelayUnit() {
        return Optional.empty();
    }

    @Override
    public SchedulerBuilder setDelayUnit(TimeUnit unit) {
        return null;
    }

    @Override
    public SchedulerBuilder setExecutor(Runnable runnable) {
        return null;
    }

    @Override
    public Runnable getExecutor() {
        return null;
    }

    @Override
    public SchedulerBuilder setToRunAfter(Scheduler scheduler) {
        return null;
    }

    @Override
    public Optional<Scheduler> getToRunAfter() {
        return Optional.empty();
    }

    @Override
    public Scheduler build(Plugin plugin) {
        if(this.executor == null){
            System.err.println("SchedulerBuilder was attempted to be built but no executor was set");
            new IOException("No Executor in build").printStackTrace();
        }
        return new BScheduler(this, plugin);
    }


























}
