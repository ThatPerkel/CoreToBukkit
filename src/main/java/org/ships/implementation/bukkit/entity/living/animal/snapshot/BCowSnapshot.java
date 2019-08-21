package org.ships.implementation.bukkit.entity.living.animal.snapshot;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.AgeableEntity;
import org.core.entity.living.animal.cow.CowSnapshot;
import org.core.entity.living.animal.cow.LiveCow;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.animal.live.BLiveCow;

public class BCowSnapshot extends BEntitySnapshot<LiveCow> implements CowSnapshot {

    private boolean adult;

    public BCowSnapshot(ExactPosition position) {
        super(position);
    }

    public BCowSnapshot(Entity entity) {
        super(entity);
    }

    @Override
    public LiveCow spawnEntity() {
        BLiveCow cow = new BLiveCow(this);
        cow.setAdult(this.adult);
        return cow;
    }

    @Override
    public EntityType<LiveCow, CowSnapshot> getType() {
        return EntityTypes.COW;
    }

    @Override
    public EntitySnapshot<LiveCow> createSnapshot() {
        return new BCowSnapshot(this);
    }

    @Override
    public boolean isAdult() {
        return this.adult;
    }

    @Override
    public AgeableEntity setAdult(boolean check) {
        this.adult = check;
        return this;
    }
}
