package org.ships.implementation.bukkit.world.expload;

import org.core.entity.Entity;
import org.core.world.expload.Explosion;
import org.core.world.position.BlockPosition;

import java.util.Collection;
import java.util.Collections;

public class EntityExplosion implements Explosion.EntityExplosion {

    protected Entity source;
    protected Collection<BlockPosition> affected;

    public EntityExplosion(Entity entity, Collection<BlockPosition> affected){
        this.source = entity;
        this.affected = affected;
    }

    @Override
    public Collection<BlockPosition> getAffectedPositions() {
        return Collections.unmodifiableCollection(this.affected);
    }

    @Override
    public Entity getSource() {
        return this.source;
    }
}
