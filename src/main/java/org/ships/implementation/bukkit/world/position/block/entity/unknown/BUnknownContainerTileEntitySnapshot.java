package org.ships.implementation.bukkit.world.position.block.entity.unknown;

import org.core.exceptions.BlockNotSupported;
import org.core.inventory.inventories.general.block.UnknownBlockAttachedInventory;
import org.core.inventory.inventories.snapshots.block.UnknownBlockAttachedInventorySnapshot;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.container.unknown.LiveUnknownContainerTileEntity;
import org.core.world.position.block.entity.container.unknown.UnknownContainerTileEntitySnapshot;
import org.core.world.position.block.entity.container.unknown.UnknownContainerTiledEntity;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.BUnknownBlockAttachedInventorySnapshot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class BUnknownContainerTileEntitySnapshot implements UnknownContainerTileEntitySnapshot {

    protected UnknownBlockAttachedInventorySnapshot inventory;

    public BUnknownContainerTileEntitySnapshot(BlockPosition position, BlockType... types){
        this.inventory = new BUnknownBlockAttachedInventorySnapshot(position, types);
    }

    public BUnknownContainerTileEntitySnapshot(UnknownContainerTiledEntity entity){
        this.inventory = entity.getInventory().createSnapshot();
    }

    @Override
    public LiveUnknownContainerTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTile = position.getTileEntity();
        if(!opTile.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), "UnknownContainerTileEntity");
        }
        LiveTileEntity lte = opTile.get();
        if(!(lte instanceof UnknownContainerTiledEntity)){
            throw new BlockNotSupported(position.getBlockType(), "UnknownContainerTileEntity");
        }
        LiveUnknownContainerTileEntity lucte = (LiveUnknownContainerTileEntity)lte;
        this.inventory.apply(lucte.getInventory());
        return lucte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(this.inventory.getAllowedBlockType());
    }

    @Override
    public UnknownContainerTileEntitySnapshot getSnapshot() {
        return new BUnknownContainerTileEntitySnapshot(this);
    }

    @Override
    public UnknownBlockAttachedInventory getInventory() {
        return this.inventory;
    }
}
