package org.ships.implementation.bukkit.world.position.block.entity.container.dispenser;

import org.core.exceptions.BlockNotSupported;
import org.core.inventory.inventories.general.block.dispenser.DispenserInventory;
import org.core.inventory.inventories.snapshots.block.dispenser.DispenserInventorySnapshot;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.container.dispenser.DispenserTileEntity;
import org.core.world.position.block.entity.container.dispenser.DispenserTileEntitySnapshot;
import org.core.world.position.block.entity.container.dispenser.LiveDispenserTileEntity;
import org.core.world.position.block.entity.container.dropper.DropperTileEntitySnapshot;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.dispenser.BDispenserInventorySnapshot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class BDispenserTileEntitySnapshot implements DispenserTileEntitySnapshot {

    protected DispenserInventorySnapshot dis;

    public BDispenserTileEntitySnapshot(){
        this.dis = new BDispenserInventorySnapshot();
    }

    public BDispenserTileEntitySnapshot(DispenserTileEntity dte){
        this.dis = dte.getInventory().createSnapshot();
    }

    @Override
    public LiveDispenserTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTE =position.getTileEntity();
        if(!opTE.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), DropperTileEntitySnapshot.class.getSimpleName());
        }
        LiveTileEntity lte = opTE.get();
        if(!(lte instanceof LiveDispenserTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), DropperTileEntitySnapshot.class.getSimpleName());
        }
        LiveDispenserTileEntity ldte = (LiveDispenserTileEntity)lte;
        this.dis.apply(ldte.getInventory());
        return ldte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(BlockTypes.DISPENSER.get());
    }

    @Override
    public DispenserInventory getInventory() {
        return this.dis;
    }

    @Override
    public DispenserTileEntitySnapshot getSnapshot() {
        return new BDispenserTileEntitySnapshot(this);
    }
}
