package org.ships.implementation.bukkit.world.position.block.entity.furnace;

import org.core.inventory.inventories.FurnaceInventory;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.container.furnace.FurnaceLiveTileEntity;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
import org.ships.implementation.bukkit.inventory.inventories.live.BLiveFurnaceInventory;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

public class BLiveFurnaceTileEntity implements FurnaceLiveTileEntity {

    protected org.bukkit.block.Furnace furnace;

    @Deprecated
    public BLiveFurnaceTileEntity(org.bukkit.block.BlockState state){
        this((org.bukkit.block.Furnace)state);
    }

    public BLiveFurnaceTileEntity(org.bukkit.block.Furnace furnace){
        this.furnace = furnace;
    }

    @Override
    public BlockPosition getPosition() {
        return new BBlockPosition(this.furnace.getBlock());
    }

    @Override
    public FurnaceInventory getInventory() {
        return new BLiveFurnaceInventory(this.furnace);
    }

    @Override
    public FurnaceTileEntitySnapshot getSnapshot() {
        return new BFurnaceTileEntitySnapshot(this);
    }
}
