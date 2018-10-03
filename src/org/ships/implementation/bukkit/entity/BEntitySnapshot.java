package org.ships.implementation.bukkit.entity;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.core.world.position.Position;

import java.util.Collection;
import java.util.HashSet;

public abstract class BEntitySnapshot <T extends Entity> implements EntitySnapshot<T> {

    protected double pitch;
    protected double yaw;
    protected double roll;
    protected ExactPosition position;
    protected Collection<Entity> passengers = new HashSet<>();

    public BEntitySnapshot(ExactPosition position){
        this.position = position;
    }

    @Override
    public Entity setPitch(double value) {
        this.pitch = value;
        return this;
    }

    @Override
    public Entity setYaw(double value) {
        this.yaw = value;
        return this;
    }

    @Override
    public Entity setRoll(double value) {
        this.roll = value;
        return this;
    }

    @Override
    public Entity setPosition(Position<? extends Number> position) {
        this.position = position instanceof ExactPosition ? (ExactPosition)position : ((BlockPosition)position).toExactPosition();
        return this;
    }

    @Override
    public double getPitch() {
        return this.pitch;
    }

    @Override
    public double getYaw() {
        return this.yaw;
    }

    @Override
    public double getRoll() {
        return this.roll;
    }

    @Override
    public Collection<Entity> getPassengers() {
        return this.passengers;
    }

    @Override
    public Entity addPassengers(Collection<Entity> entities) {
        this.passengers.addAll(entities);
        return this;
    }

    @Override
    public Entity removePassengers(Collection<Entity> entities) {
        this.passengers.removeAll(entities);
        return this;
    }

    @Override
    public ExactPosition getPosition() {
        return this.position;
    }
}
