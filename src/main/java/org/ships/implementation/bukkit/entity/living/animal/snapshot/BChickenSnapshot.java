package org.ships.implementation.bukkit.entity.living.animal.snapshot;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.AgeableEntity;
import org.core.entity.living.animal.chicken.ChickenSnapshot;
import org.core.entity.living.animal.chicken.LiveChicken;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.animal.live.BLiveChicken;

public class BChickenSnapshot extends BEntitySnapshot<LiveChicken> implements ChickenSnapshot {

    private boolean adult;

    public BChickenSnapshot(ExactPosition position) {
        super(position);
    }

    public BChickenSnapshot(Entity entity) {
        super(entity);
    }

    @Override
    public LiveChicken spawnEntity() {
        LiveChicken chicken = new BLiveChicken(this);
        chicken.setAdult(this.adult);
        return chicken;
    }

    @Override
    public EntityType<LiveChicken, ChickenSnapshot> getType() {
        return EntityTypes.CHICKEN;
    }

    @Override
    public EntitySnapshot<LiveChicken> createSnapshot() {
        return new BChickenSnapshot(this);
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
