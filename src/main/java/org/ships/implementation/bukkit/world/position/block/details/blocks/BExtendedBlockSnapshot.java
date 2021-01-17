package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.bukkit.block.data.BlockData;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.block.details.data.keyed.KeyedData;
import org.core.world.position.block.details.data.keyed.TileEntityKeyedData;
import org.core.world.position.impl.sync.SyncBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;

import java.util.Optional;

public class BExtendedBlockSnapshot extends BBlockDetails implements BlockSnapshot<SyncBlockPosition> {

    protected SyncBlockPosition position;

    public BExtendedBlockSnapshot(BBlockPosition position){
        super(position);
        this.position = position;
    }

    public BExtendedBlockSnapshot(SyncBlockPosition position, BlockData data) {
        super(data, false);
        this.position = position;
    }

    @Override
    protected <T> Optional<KeyedData<T>> getKey(Class<? extends KeyedData<T>> data) {
        if(data.isAssignableFrom(TileEntityKeyedData.class)) {
            return Optional.of((KeyedData<T>) new BTileEntityKeyedData());
        }
        return super.getKey(data);
    }

    @Override
    public SyncBlockPosition getPosition() {
        return this.position;
    }

    @Override
    public BlockSnapshot<SyncBlockPosition> createCopyOf() {
        return new BExtendedBlockSnapshot(this.position, this.getBukkitData().clone());
    }
}
