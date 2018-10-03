package org.ships.implementation.bukkit.world.position.block.entity;

import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.TileEntity;
import org.core.world.position.block.entity.TileEntitySnapshot;

public abstract class AbstractTileEntitySnapshot<T extends TileEntity> implements TileEntitySnapshot<T> {

    protected BlockPosition position;

    public AbstractTileEntitySnapshot(BlockPosition position){
        this.position = position;
    }

    @Override
    public BlockPosition getPosition() {
        return this.position;
    }
}
