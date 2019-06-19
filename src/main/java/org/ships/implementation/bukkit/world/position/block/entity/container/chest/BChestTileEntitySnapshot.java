package org.ships.implementation.bukkit.world.position.block.entity.container.chest;

import org.core.exceptions.BlockNotSupported;
import org.core.inventory.inventories.general.block.ChestInventory;
import org.core.inventory.inventories.snapshots.block.ChestInventorySnapshot;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.container.chest.ChestTileEntity;
import org.core.world.position.block.entity.container.chest.ChestTileEntitySnapshot;
import org.core.world.position.block.entity.container.chest.LiveChestTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.BChestInventorySnapshot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class BChestTileEntitySnapshot implements ChestTileEntitySnapshot {

    protected ChestInventorySnapshot inventory;

    public BChestTileEntitySnapshot(){
        this.inventory = new BChestInventorySnapshot();
    }

    public BChestTileEntitySnapshot(ChestTileEntity cte){
        this.inventory = cte.getInventory().createSnapshot();
    }

    @Override
    public LiveChestTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTE =position.getTileEntity();
        if(!opTE.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), ChestTileEntitySnapshot.class.getSimpleName());
        }
        LiveTileEntity lte = opTE.get();
        if(!(lte instanceof LiveChestTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), ChestTileEntitySnapshot.class.getSimpleName());
        }
        LiveChestTileEntity lcte = (LiveChestTileEntity)lte;
        this.inventory.apply(lcte.getInventory());
        return lcte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(BlockTypes.CHEST.get(), BlockTypes.TRAPPED_CHEST.get());
    }

    @Override
    public ChestInventory getInventory() {
        return this.inventory;
    }

    @Override
    public ChestTileEntitySnapshot getSnapshot() {
        return new BChestTileEntitySnapshot(this);
    }
}
