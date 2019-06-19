package org.ships.implementation.bukkit.entity.living.animal.live;

import org.bukkit.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.living.AgeableEntity;
import org.core.entity.living.animal.chicken.LiveChicken;
import org.ships.implementation.bukkit.entity.BLiveEntity;

public class BLiveChicken extends BLiveEntity<org.bukkit.entity.Chicken> implements LiveChicken {

    @Deprecated
    public BLiveChicken(Entity entity){
        this((org.bukkit.entity.Chicken)entity);
    }

    public BLiveChicken(org.bukkit.entity.Chicken entity) {
        super(entity);
    }

    @Override
    public boolean isAdult() {
        return getBukkitEntity().isAdult();
    }

    @Override
    public AgeableEntity setAdult(boolean check) {
        if(check){
            getBukkitEntity().setAdult();
        }else{
            getBukkitEntity().setBaby();
        }
        return this;
    }


    @Override
    public EntitySnapshot createSnapshot() {
        return null;
    }
}
