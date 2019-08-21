package org.ships.implementation.bukkit.entity.living.bat.snapshot;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.bat.Bat;
import org.core.entity.living.bat.BatSnapshot;
import org.core.entity.living.bat.LiveBat;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.bat.live.BLiveBat;

public class BBatSnapshot extends BEntitySnapshot<LiveBat> implements BatSnapshot {

    protected boolean awake;

    public BBatSnapshot(ExactPosition position) {
        super(position);
    }

    public BBatSnapshot(Entity entity) {
        super(entity);
    }

    @Override
    public BLiveBat spawnEntity() {
        BLiveBat bat = new BLiveBat(this);
        bat.setAwake(this.awake);
        return bat;
    }

    @Override
    public EntityType<LiveBat, BatSnapshot> getType() {
        return EntityTypes.BAT;
    }

    @Override
    public EntitySnapshot<LiveBat> createSnapshot() {
        return new BBatSnapshot(this);
    }

    @Override
    public boolean isAwake() {
        return this.awake;
    }

    @Override
    public Bat setAwake(boolean state) {
        this.awake = state;
        return this;
    }
}
