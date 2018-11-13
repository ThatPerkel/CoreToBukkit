package org.ships.implementation.bukkit.inventory.inventories.snapshot;

import org.core.inventory.inventories.FurnaceInventory;
import org.core.inventory.inventories.snapshots.FurnaceInventorySnapshot;
import org.core.inventory.parts.snapshot.SlotSnapshot;
import org.ships.implementation.bukkit.inventory.inventories.live.BLiveFurnaceInventory;

public class BFurnaceInventorySnapshot extends FurnaceInventorySnapshot {

    public BFurnaceInventorySnapshot(){
        this.fuelSlot = new SlotSnapshot(BLiveFurnaceInventory.FUEL_POSITION, null);
        this.resultsSlot = new SlotSnapshot(BLiveFurnaceInventory.RESULTS_POSITION, null);
        this.smeltingSlot = new SlotSnapshot(BLiveFurnaceInventory.SMELTING_POSITION, null);
    }

    public BFurnaceInventorySnapshot(FurnaceInventory fi){
        this.fuelSlot = fi.getFuelSlot().createSnapshot();
        this.resultsSlot = fi.getResultsSlot().createSnapshot();
        this.smeltingSlot = fi.getSmeltingSlot().createSnapshot();
    }

    @Override
    public FurnaceInventorySnapshot createSnapshot() {
        return new BFurnaceInventorySnapshot(this);
    }
}
