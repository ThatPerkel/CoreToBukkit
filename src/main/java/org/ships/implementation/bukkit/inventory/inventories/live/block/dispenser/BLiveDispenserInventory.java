package org.ships.implementation.bukkit.inventory.inventories.live.block.dispenser;

import org.bukkit.block.Container;
import org.core.inventory.inventories.general.block.dispenser.DispenserInventory;
import org.core.inventory.inventories.snapshots.block.dispenser.DispenserInventorySnapshot;
import org.core.inventory.parts.Slot;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.dispenser.BDispenserInventorySnapshot;

import java.util.Optional;

public class BLiveDispenserInventory extends BLiveDispenserBasedInventory implements DispenserInventory {

    org.bukkit.block.Dispenser dispenser;

    public BLiveDispenserInventory(org.bukkit.block.Dispenser dispenser){
        this.dispenser = dispenser;
    }

    @Override
    public DispenserInventorySnapshot createSnapshot() {
        return new BDispenserInventorySnapshot(this);
    }

    @Override
    protected Container getBukkitBlockState() {
        return this.dispenser;
    }
}
