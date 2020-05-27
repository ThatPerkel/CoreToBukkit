package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.bukkit.block.data.BlockData;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.impl.async.ASyncBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.async.BAsyncBlockPosition;

public class AsyncBlockStateSnapshot extends BBlockDetails implements BlockSnapshot<ASyncBlockPosition> {

    protected ASyncBlockPosition position;

    public AsyncBlockStateSnapshot(BAsyncBlockPosition position){
        super(position);
        this.position = position;
    }

    public AsyncBlockStateSnapshot(ASyncBlockPosition position, BlockData data) {
        super(data, true);
        this.position = position;
    }

    @Override
    public ASyncBlockPosition getPosition() {
        return this.position;
    }

    @Override
    public BlockSnapshot<ASyncBlockPosition> createCopyOf() {
        return new AsyncBlockStateSnapshot(this.position, this.getBukkitData().clone());
    }
}
