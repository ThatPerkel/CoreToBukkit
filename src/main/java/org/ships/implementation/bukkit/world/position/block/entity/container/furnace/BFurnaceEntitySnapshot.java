package org.ships.implementation.bukkit.world.position.block.entity.container.furnace;

import org.core.inventory.inventories.snapshots.block.FurnaceInventorySnapshot;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntity;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
import org.core.world.position.block.entity.container.furnace.LiveFurnaceTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.BFurnaceInventorySnapshot;

import java.util.Arrays;
import java.util.Collection;

public class BFurnaceEntitySnapshot implements FurnaceTileEntitySnapshot {

    protected FurnaceInventorySnapshot inventory;

    public BFurnaceEntitySnapshot() {
        this.inventory = new BFurnaceInventorySnapshot();
    }

    public BFurnaceEntitySnapshot(FurnaceTileEntity fte) {
        this.inventory = fte.getInventory().createSnapshot();
    }

    @Override
    public LiveFurnaceTileEntity apply(LiveFurnaceTileEntity lfte) {
        this.inventory.apply(lfte.getInventory());
        return lfte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(BlockTypes.FURNACE.get());
    }

    @Override
    public FurnaceInventorySnapshot getInventory() {
        return this.inventory;
    }

    @Override
    public FurnaceTileEntitySnapshot getSnapshot() {
        return new BFurnaceEntitySnapshot(this);
    }
}

