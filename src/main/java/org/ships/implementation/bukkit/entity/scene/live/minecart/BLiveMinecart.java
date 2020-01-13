package org.ships.implementation.bukkit.entity.scene.live.minecart;

import org.bukkit.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.LiveEntity;
import org.core.entity.scene.minecart.LiveMinecart;
import org.core.entity.scene.minecart.Minecart;
import org.ships.implementation.bukkit.entity.BLiveEntity;

public abstract class BLiveMinecart<M extends org.bukkit.entity.Minecart> extends BLiveEntity<M> implements LiveMinecart {

    public BLiveMinecart(Entity entity) {
        this((M)entity);
    }

    public BLiveMinecart(M minecart){
        super(minecart);
    }

    @Override
    public boolean isSlowWhenEmpty() {
        return this.entity.isSlowWhenEmpty();
    }

    @Override
    public Minecart<LiveEntity> setSlowWhenEmpty(boolean check) {
        this.entity.setSlowWhenEmpty(check);
        return this;
    }
}
