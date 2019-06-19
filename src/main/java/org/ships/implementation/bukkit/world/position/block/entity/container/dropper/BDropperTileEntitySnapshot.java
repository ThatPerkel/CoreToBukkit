package org.ships.implementation.bukkit.world.position.block.entity.container.dropper;

import org.core.exceptions.BlockNotSupported;
import org.core.inventory.inventories.general.block.dispenser.DropperInventory;
import org.core.inventory.inventories.snapshots.block.dispenser.DropperInventorySnapshot;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.container.dropper.DropperTileEntity;
import org.core.world.position.block.entity.container.dropper.DropperTileEntitySnapshot;
import org.core.world.position.block.entity.container.dropper.LiveDropperTileEntity;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.dispenser.BDropperInventorySnapshot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class BDropperTileEntitySnapshot implements DropperTileEntitySnapshot {

    protected DropperInventorySnapshot inventory;

    public BDropperTileEntitySnapshot(){
        inventory = new BDropperInventorySnapshot();
    }

    public BDropperTileEntitySnapshot(DropperTileEntity dte){
        this.inventory = dte.getInventory().createSnapshot();
    }

    @Override
    public LiveDropperTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTE =position.getTileEntity();
        if(!opTE.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), DropperTileEntitySnapshot.class.getSimpleName());
        }
        LiveTileEntity lte = opTE.get();
        if(!(lte instanceof LiveDropperTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), DropperTileEntitySnapshot.class.getSimpleName());
        }
        LiveDropperTileEntity ldte = (LiveDropperTileEntity)lte;
        this.inventory.apply(ldte.getInventory());
        return ldte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(BlockTypes.DROPPER.get());
    }

    @Override
    public DropperInventory getInventory() {
        return this.inventory;
    }

    @Override
    public DropperTileEntitySnapshot getSnapshot() {
        return new BDropperTileEntitySnapshot(this);
    }
}
