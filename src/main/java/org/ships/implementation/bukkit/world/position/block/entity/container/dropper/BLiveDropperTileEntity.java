package org.ships.implementation.bukkit.world.position.block.entity.container.dropper;

import org.core.inventory.inventories.general.block.dispenser.DropperInventory;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.container.dropper.DropperTileEntitySnapshot;
import org.core.world.position.block.entity.container.dropper.LiveDropperTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.live.block.dispenser.BLiveDropperInventory;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

public class BLiveDropperTileEntity implements LiveDropperTileEntity {

    protected org.bukkit.block.Dropper dropper;

    @Deprecated
    public BLiveDropperTileEntity(org.bukkit.block.BlockState state){
        this((org.bukkit.block.Dropper)state);
    }

    public BLiveDropperTileEntity(org.bukkit.block.Dropper dropper){
       this.dropper = dropper;
    }

    @Override
    public BlockPosition getPosition() {
        return new BBlockPosition(this.dropper.getBlock());
    }

    @Override
    public DropperInventory getInventory() {
        return new BLiveDropperInventory(this.dropper);
    }

    @Override
    public DropperTileEntitySnapshot getSnapshot() {
        return new BDropperTileEntitySnapshot();
    }
}
