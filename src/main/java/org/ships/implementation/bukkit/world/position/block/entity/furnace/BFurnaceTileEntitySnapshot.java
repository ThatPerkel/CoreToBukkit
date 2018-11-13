package org.ships.implementation.bukkit.world.position.block.entity.furnace;

import org.core.exceptions.BlockNotSupported;
import org.core.inventory.inventories.FurnaceInventory;
import org.core.inventory.inventories.snapshots.FurnaceInventorySnapshot;
import org.core.inventory.item.ItemStack;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntity;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.BFurnaceInventorySnapshot;

import java.util.Optional;

public class BFurnaceTileEntitySnapshot implements FurnaceTileEntitySnapshot {

    protected FurnaceInventorySnapshot inventory;

    public BFurnaceTileEntitySnapshot() {
        this.inventory = new BFurnaceInventorySnapshot();
    }

    public BFurnaceTileEntitySnapshot(FurnaceTileEntity fte){
        fte.getInventory().getSlots().stream().forEach(s -> System.out.println("\t\t\t- " + s.getPosition().orElse(-1) + " - " + s.getItem().orElse(null)));
        this.inventory = fte.getInventory().createSnapshot();
        this.inventory.getSlots().stream().forEach(s -> System.out.println("\t\t\t- " + s.getPosition().orElse(-1) + " - " + s.getItem().orElse(null)));
    }

    @Override
    public FurnaceTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTE = position.getTileEntity();
        if(!opTE.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), "FurnaceTileEntity");
        }
        LiveTileEntity lte = opTE.get();
        if(!(lte instanceof FurnaceTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), "FurnaceTileEntity");
        }
        FurnaceTileEntity fte = (FurnaceTileEntity) lte;
        FurnaceInventory fi = fte.getInventory();
        this.inventory.getFuelSlot().getItem().ifPresent(i -> System.out.println("FuelSlot: " + i.getType().getId() + " - " + i.getQuantity()));
        ItemStack fuel = this.inventory.getFuelSlot().getItem().orElse(null);
        ItemStack result = this.inventory.getResultsSlot().getItem().orElse(null);
        ItemStack smelting = this.inventory.getSmeltingSlot().getItem().orElse(null);
        if(fuel != null){
            fuel = fuel.copy();
        }
        if(result != null){
            result = result.copy();
        }
        if(fuel != null){
            result = result.copy();
        }
        fi.getFuelSlot().setItem(fuel);
        fi.getResultsSlot().setItem(result);
        fi.getSmeltingSlot().setItem(smelting);
        fi.getFuelSlot().getItem().ifPresent(i -> System.out.println("Real FuelSlot: " + i.getType().getId() + " - " + i.getQuantity()));
        return fte;
    }

    @Override
    public FurnaceInventorySnapshot getInventory() {
        return this.inventory;
    }

    @Override
    public FurnaceTileEntitySnapshot getSnapshot() {
        return new BFurnaceTileEntitySnapshot(this);
    }
}
