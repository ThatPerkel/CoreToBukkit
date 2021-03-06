package org.ships.implementation.bukkit.world.position.block.entity.container.furnace;

import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.core.inventory.inventories.general.block.FurnaceInventory;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
import org.core.world.position.block.entity.container.furnace.LiveFurnaceTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.live.block.BLiveFurnaceInventory;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractLiveTileEntity;

public class BFurnaceEntity extends AbstractLiveTileEntity implements LiveFurnaceTileEntity {

    public BFurnaceEntity(BlockState state) {
        super(state);
    }

    public org.bukkit.block.Furnace getBukkitFurnace(){
        return (Furnace) this.state;
    }

    @Override
    public FurnaceInventory getInventory() {
        return new BLiveFurnaceInventory(this.getBukkitFurnace());
    }

    @Override
    public FurnaceTileEntitySnapshot getSnapshot() {
        return new BFurnaceEntitySnapshot(this);
    }
}
