package org.ships.implementation.bukkit.entity.living.hostile.creeper;

import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.hostile.creeper.CreeperEntitySnapshot;
import org.core.entity.living.hostile.creeper.LiveCreeperEntity;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.world.position.BExactPosition;

public class BCreeperSnapshot extends BEntitySnapshot<LiveCreeperEntity> implements CreeperEntitySnapshot {

    public BCreeperSnapshot(ExactPosition position) {
        super(position);
    }

    public BCreeperSnapshot(LiveCreeperEntity entity) {
        super(entity);
    }

    public BCreeperSnapshot(EntitySnapshot<LiveCreeperEntity> entity) {
        super(entity);
    }

    @Override
    public LiveCreeperEntity spawnEntity() {
        org.bukkit.Location loc = ((BExactPosition)this.position).getBukkitLocation();
        loc.setPitch((float)this.pitch);
        loc.setYaw((float)this.yaw);
        org.bukkit.entity.Creeper creeper = (org.bukkit.entity.Creeper)loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.CREEPER);
        BLiveCreeper liveCreeper = new BLiveCreeper(creeper);
        applyDefaults(liveCreeper);
        return liveCreeper;
    }

    @Override
    public EntityType<LiveCreeperEntity, CreeperEntitySnapshot> getType() {
        return EntityTypes.CREEPER;
    }

    @Override
    public EntitySnapshot<LiveCreeperEntity> createSnapshot() {
        return new BCreeperSnapshot(this);
    }
}
