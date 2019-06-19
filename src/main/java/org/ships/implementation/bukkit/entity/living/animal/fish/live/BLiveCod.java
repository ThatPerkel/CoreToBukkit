package org.ships.implementation.bukkit.entity.living.animal.fish.live;

import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.fish.cod.Cod;
import org.core.entity.living.fish.cod.CodSnapshot;
import org.core.entity.living.fish.cod.LiveCod;
import org.ships.implementation.bukkit.entity.BLiveEntity;

public class BLiveCod extends BLiveEntity<org.bukkit.entity.Cod> implements LiveCod {

    @Deprecated
    public BLiveCod(org.bukkit.entity.Entity entity){
        super((org.bukkit.entity.Cod)entity);
    }

    public BLiveCod(org.bukkit.entity.Cod entity) {
        super(entity);
    }

    @Override
    public EntityType<Cod, CodSnapshot> getType() {
        return EntityTypes.COD;
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return null;
    }
}
