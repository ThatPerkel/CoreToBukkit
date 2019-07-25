package org.ships.implementation.bukkit.event.events.entity;

import org.core.entity.LiveEntity;
import org.core.event.events.entity.EntitySpawnEvent;
import org.core.world.position.ExactPosition;

public class BEntitySpawnEvent implements EntitySpawnEvent {

    boolean cancel;
    LiveEntity entity;
    ExactPosition position;

    public BEntitySpawnEvent(ExactPosition position, LiveEntity entity){
        this.entity = entity;
        this.position = position;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean value) {
        this.cancel = value;
    }

    @Override
    public LiveEntity getEntity() {
        return this.entity;
    }

    @Override
    public ExactPosition getPosition() {
        return this.position;
    }
}
