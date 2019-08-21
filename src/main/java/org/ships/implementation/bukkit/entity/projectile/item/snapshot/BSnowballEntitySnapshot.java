package org.ships.implementation.bukkit.entity.projectile.item.snapshot;

import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.projectile.ProjectileEntity;
import org.core.entity.projectile.item.snowball.LiveSnowballEntity;
import org.core.entity.projectile.item.snowball.SnowballEntity;
import org.core.entity.projectile.item.snowball.SnowballEntitySnapshot;
import org.core.source.projectile.ProjectileSource;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.projectile.item.live.BLiveSnowballEntity;

import java.util.Optional;

public class BSnowballEntitySnapshot extends BEntitySnapshot<LiveSnowballEntity> implements SnowballEntitySnapshot {

    private ProjectileSource source;

    public BSnowballEntitySnapshot(ExactPosition position) {
        super(position);
    }

    public BSnowballEntitySnapshot(SnowballEntity entity) {
        super(entity);
    }

    @Override
    public BLiveSnowballEntity spawnEntity() {
        BLiveSnowballEntity snowball = new BLiveSnowballEntity(this);
        snowball.setSource(this.source);
        return snowball;
    }

    @Override
    public EntityType<LiveSnowballEntity, SnowballEntitySnapshot> getType() {
        return EntityTypes.SNOWBALL;
    }

    @Override
    public EntitySnapshot<LiveSnowballEntity> createSnapshot() {
        return new BSnowballEntitySnapshot(this);
    }

    @Override
    public Optional<ProjectileSource> getSource() {
        return Optional.ofNullable(this.source);
    }

    @Override
    public ProjectileEntity setSource(ProjectileSource source) {
        this.source = source;
        return this;
    }
}
