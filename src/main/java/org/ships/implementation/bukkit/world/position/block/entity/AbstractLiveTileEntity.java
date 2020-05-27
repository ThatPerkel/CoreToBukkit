package org.ships.implementation.bukkit.world.position.block.entity;

import org.core.world.position.impl.sync.SyncBlockPosition;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;

public abstract class AbstractLiveTileEntity implements LiveTileEntity {

    protected org.bukkit.block.BlockState state;

    public AbstractLiveTileEntity(org.bukkit.block.BlockState state){
        this.state = state;
    }

    @Override
    public SyncBlockPosition getPosition() {
        return new BBlockPosition(state.getBlock());
    }
}
