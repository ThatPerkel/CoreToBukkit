package org.ships.implementation.bukkit.world.position.block.entity;

import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

public abstract class AbstractTileEntity implements LiveTileEntity {

    protected org.bukkit.block.BlockState state;

    public AbstractTileEntity(org.bukkit.block.BlockState state){
        this.state = state;
    }

    @Override
    public BlockPosition getPosition() {
        return new BBlockPosition(state.getBlock());
    }
}
