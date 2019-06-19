package org.ships.implementation.bukkit.world.position.block.entity.container.chest;

import org.bukkit.block.Chest;
import org.core.inventory.inventories.general.block.ChestInventory;
import org.core.world.position.block.entity.container.chest.ChestTileEntitySnapshot;
import org.core.world.position.block.entity.container.chest.LiveChestTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.live.block.BLiveChestInventory;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractLiveTileEntity;

public class BLiveChestTileEntity extends AbstractLiveTileEntity implements LiveChestTileEntity {

    @Deprecated
    public BLiveChestTileEntity(org.bukkit.block.BlockState state){
        this((org.bukkit.block.Chest)state);
    }

    public BLiveChestTileEntity(org.bukkit.block.Chest state) {
        super(state);
    }

    public Chest getBukkitTileEntity(){
        return (Chest) this.state;
    }

    @Override
    public ChestInventory getInventory() {
        return new BLiveChestInventory(getBukkitTileEntity());
    }

    @Override
    public ChestTileEntitySnapshot getSnapshot() {
        return new BChestTileEntitySnapshot(this);
    }
}
