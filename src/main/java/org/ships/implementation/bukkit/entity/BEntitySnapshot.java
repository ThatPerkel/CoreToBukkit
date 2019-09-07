package org.ships.implementation.bukkit.entity;

import org.core.CorePlugin;
import org.core.entity.EntitySnapshot;
import org.core.entity.LiveEntity;
import org.core.text.Text;
import org.core.vector.types.Vector3Double;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.core.world.position.Position;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public abstract class BEntitySnapshot <T extends LiveEntity> implements EntitySnapshot<T> {

    protected double pitch;
    protected double yaw;
    protected double roll;
    protected ExactPosition position;
    protected Collection<EntitySnapshot<? extends LiveEntity>> passengers = new HashSet<>();
    protected boolean hasGravity;
    protected Vector3Double velocity;
    protected Text customName;
    protected boolean isCustomNameVisible;
    protected T createdFrom;

    public BEntitySnapshot(ExactPosition position){
        this.position = position;
    }

    public BEntitySnapshot(T entity){
        this.hasGravity = entity.hasGravity();
        this.customName = entity.getCustomName();
        this.velocity = entity.getVelocity();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
        this.roll = entity.getRoll();
        this.position = entity.getPosition();
        entity.getPassengers().stream().forEach(e -> this.passengers.add(e.createSnapshot()));
        this.createdFrom = entity;
    }

    public BEntitySnapshot(EntitySnapshot<T> entity){
        this.hasGravity = entity.hasGravity();
        this.customName = entity.getCustomName();
        this.velocity = entity.getVelocity();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
        this.roll = entity.getRoll();
        this.position = entity.getPosition();
        this.passengers.addAll(entity.getPassengers());
        this.createdFrom = entity.getCreatedFrom().orElse(null);
    }

    protected <L extends LiveEntity> L applyDefaults(L entity){
        entity.setCustomNameVisible(this.isCustomNameVisible);
        if(this.customName != null) {
            entity.setCustomName(this.customName);
        }
        entity.setGravity(this.hasGravity);
        entity.setVelocity(this.velocity);
        entity.setPosition(this.position);
        entity.setPitch(this.pitch);
        entity.setRoll(this.roll);
        entity.setYaw(this.yaw);
        return entity;
    }

    @Override
    public EntitySnapshot<T> setPitch(double value) {
        this.pitch = value;
        return this;
    }

    @Override
    public EntitySnapshot<T> setYaw(double value) {
        this.yaw = value;
        return this;
    }

    @Override
    public EntitySnapshot<T> setRoll(double value) {
        this.roll = value;
        return this;
    }

    @Override
    public EntitySnapshot<T> setPosition(Position<? extends Number> position) {
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
    public Collection<EntitySnapshot<? extends LiveEntity>> getPassengers() {
        return this.passengers;
    }

    @Override
    public EntitySnapshot<T> addPassengers(Collection<EntitySnapshot<? extends LiveEntity>> entities) {
        this.passengers.addAll(entities);
        return this;
    }

    @Override
    public EntitySnapshot<T> removePassengers(Collection<EntitySnapshot<? extends LiveEntity>> entities) {
        this.passengers.removeAll(entities);
        return this;
    }

    @Override
    public ExactPosition getPosition() {
        return this.position;
    }

    @Override
    public EntitySnapshot<T> setGravity(boolean check) {
        this.hasGravity = check;
        return this;
    }

    @Override
    public boolean hasGravity() {
        return this.hasGravity;
    }

    @Override
    public EntitySnapshot<T> setVelocity(Vector3Double velocity){
        this.velocity = velocity;
        return this;
    }

    @Override
    public Vector3Double getVelocity(){
        return this.velocity;
    }

    @Override
    public EntitySnapshot<T> setCustomName(Text text){
        this.customName = text;
        return this;
    }

    @Override
    public Text getCustomName(){
        if(this.customName == null){
            BEntityType<T, ? extends EntitySnapshot<T>> type = (BEntityType<T, ? extends EntitySnapshot<T>>) this.getType();
            return CorePlugin.buildText(type.getBukkitEntityType().name().toLowerCase());
        }
        return this.customName;
    }

    @Override
    public EntitySnapshot<T> setCustomNameVisible(boolean visible){
        this.isCustomNameVisible = visible;
        return this;
    }

    @Override
    public boolean isCustomNameVisible(){
        return this.isCustomNameVisible;
    }

    @Override
    public Optional<T> getCreatedFrom(){
        return Optional.ofNullable(this.createdFrom);
    }
}
