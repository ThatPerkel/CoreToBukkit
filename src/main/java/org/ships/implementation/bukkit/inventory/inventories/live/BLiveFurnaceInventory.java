package org.ships.implementation.bukkit.inventory.inventories.live;

import org.core.inventory.inventories.live.LiveFurnaceInventory;
import org.core.inventory.inventories.snapshots.FurnaceInventorySnapshot;
import org.core.inventory.item.ItemStack;
import org.core.inventory.parts.Slot;
import org.core.world.position.BlockPosition;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.BFurnaceInventorySnapshot;
import org.ships.implementation.bukkit.inventory.item.BItemStack;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

import java.util.Optional;

public class BLiveFurnaceInventory implements LiveFurnaceInventory {

    public static final int FUEL_POSITION = 0;
    public static final int SMELTING_POSITION = 1;
    public static final int RESULTS_POSITION = 2;

    private class FurnaceItemSlot implements Slot {

        private int position;

        public FurnaceItemSlot(int pos){
            this.position = pos;
        }

        @Override
        public Optional<Integer> getPosition() {
            return Optional.of(this.position);
        }

        @Override
        public Optional<ItemStack> getItem() {
            org.bukkit.inventory.ItemStack is = BLiveFurnaceInventory.this.furnace.getInventory().getItem(this.position);
            if(is == null){
                return Optional.empty();
            }
            ItemStack isC = new BItemStack(is);
            return Optional.of(isC);
        }

        @Override
        public Slot setItem(ItemStack stack) {
            org.bukkit.inventory.ItemStack is = stack == null ? null : ((BItemStack) stack).getBukkitItem();
            BLiveFurnaceInventory.this.furnace.getInventory().setItem(this.position, is);
            System.out.println("Setting Position: " + this.position + " to " + stack + ". Is now: " + BLiveFurnaceInventory.this.furnace.getInventory().getItem(this.position));
            BLiveFurnaceInventory.this.furnace.update(true);
            return this;
        }
    }

    protected org.bukkit.block.Furnace furnace;
    protected BLiveFurnaceInventory.FurnaceItemSlot result = new BLiveFurnaceInventory.FurnaceItemSlot(RESULTS_POSITION);
    protected BLiveFurnaceInventory.FurnaceItemSlot fuel = new BLiveFurnaceInventory.FurnaceItemSlot(FUEL_POSITION);
    protected BLiveFurnaceInventory.FurnaceItemSlot smelting = new BLiveFurnaceInventory.FurnaceItemSlot(SMELTING_POSITION);


    public BLiveFurnaceInventory(org.bukkit.block.Furnace furnace){
        this.furnace = furnace;
    }

    @Override
    public Slot getFuelSlot() {
        return this.fuel;
    }

    @Override
    public Slot getResultsSlot() {
        return this.result;
    }

    @Override
    public Slot getSmeltingSlot(){
        return this.smelting;
    }

    @Override
    public FurnaceInventorySnapshot createSnapshot() {
        return new BFurnaceInventorySnapshot(this);
    }

    @Override
    public BlockPosition getPosition() {
        return new BBlockPosition(this.furnace.getBlock());
    }
}
