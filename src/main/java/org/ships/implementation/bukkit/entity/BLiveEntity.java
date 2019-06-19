package org.ships.implementation.bukkit.entity;

import org.core.entity.Entity;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.core.world.position.Position;
import org.ships.implementation.bukkit.world.position.BExactPosition;

import java.util.Collection;

public abstract class BLiveEntity<T extends org.bukkit.entity.Entity> implements Entity {

    protected T entity;

    public BLiveEntity(T entity){
        this.entity = entity;
    }

    public T getBukkitEntity(){
        return this.entity;
    }

    @Override
    public Entity setGravity(boolean check) {
        this.entity.setGravity(check);
        return this;
    }

    @Override
    public boolean hasGravity() {
        return this.entity.hasGravity();
    }

    @Override
    public Entity setPitch(double value) {
        org.bukkit.Location loc = this.entity.getLocation();
        loc.setPitch((float)value);
        entity.teleport(loc);
        return this;
    }

    @Override
    public Entity setYaw(double value) {
        org.bukkit.Location loc = this.entity.getLocation();
        loc.setYaw((float)value);
        entity.teleport(loc);
        return this;
    }

    @Override
    public Entity setRoll(double value) {
        return this;
    }

    @Override
    public Entity setPosition(Position<? extends Number> position) {
        BExactPosition position1 = position instanceof BExactPosition ? (BExactPosition)position : (BExactPosition) ((BlockPosition)position).toExactPosition();
        this.entity.teleport(position1.getBukkitLocation());
        return this;
    }

    @Override
    public double getPitch() {
        return this.entity.getLocation().getPitch();
    }

    @Override
    public double getYaw() {
        return this.entity.getLocation().getYaw();
    }

    @Override
    public double getRoll() {
        return 0;
    }

    @Override
    public Collection<Entity> getPassengers() {
        return null;
    }

    @Override
    public Entity addPassengers(Collection<Entity> entities) {
        return null;
    }

    @Override
    public Entity removePassengers(Collection<Entity> entities) {
        return null;
    }

    @Override
    public ExactPosition getPosition() {
        return new BExactPosition(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getWorld());
    }
}
