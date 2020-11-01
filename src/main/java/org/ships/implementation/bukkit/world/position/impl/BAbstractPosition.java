package org.ships.implementation.bukkit.world.position.impl;

import org.core.world.position.impl.Position;

public abstract class BAbstractPosition<T extends Number> implements Position<T> {

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Position)){
            return false;
        }
        Position<? extends Number> pos = (Position<? extends Number>)obj;
        if(!pos.getX().equals(this.getX())){
            return false;
        }
        if(!pos.getY().equals(this.getY())){
            return false;
        }
        if(!pos.getZ().equals(this.getZ())){
            return false;
        }
        return pos.getWorld().equals(this.getWorld());
    }
}
