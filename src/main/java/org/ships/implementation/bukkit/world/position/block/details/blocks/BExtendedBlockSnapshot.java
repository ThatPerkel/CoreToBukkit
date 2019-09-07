package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.bukkit.block.data.BlockData;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.details.BlockSnapshot;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

public class BExtendedBlockSnapshot extends BBlockDetails implements BlockSnapshot {

    protected BlockPosition position;

    public BExtendedBlockSnapshot(BBlockPosition position){
        super(position);
        this.position = position;
    }

    public BExtendedBlockSnapshot(BlockPosition position, BlockData data) {
        super(data);
        this.position = position;
    }

    @Override
    public BlockPosition getPosition() {
        return this.position;
    }

    @Override
    public BlockSnapshot createCopyOf() {
        return new BExtendedBlockSnapshot(this.position, this.getBukkitData().clone());
    }
}
