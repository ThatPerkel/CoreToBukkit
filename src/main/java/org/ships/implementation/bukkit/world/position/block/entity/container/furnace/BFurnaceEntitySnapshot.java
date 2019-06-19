        package org.ships.implementation.bukkit.world.position.block.entity.container.furnace;

        import org.core.exceptions.BlockNotSupported;
        import org.core.inventory.inventories.snapshots.block.FurnaceInventorySnapshot;
        import org.core.world.position.BlockPosition;
        import org.core.world.position.block.BlockType;
        import org.core.world.position.block.BlockTypes;
        import org.core.world.position.block.entity.LiveTileEntity;
        import org.core.world.position.block.entity.container.furnace.FurnaceTileEntity;
        import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
        import org.core.world.position.block.entity.container.furnace.LiveFurnaceTileEntity;
        import org.ships.implementation.bukkit.inventory.inventories.snapshot.block.BFurnaceInventorySnapshot;

        import java.util.Arrays;
        import java.util.Collection;
        import java.util.Optional;

public class BFurnaceEntitySnapshot implements FurnaceTileEntitySnapshot {

    protected FurnaceInventorySnapshot inventory;

    public BFurnaceEntitySnapshot() {
        this.inventory = new BFurnaceInventorySnapshot();
    }

    public BFurnaceEntitySnapshot(FurnaceTileEntity fte){
        this.inventory = fte.getInventory().createSnapshot();
    }

    @Override
    public FurnaceTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTE =position.getTileEntity();
        if(!opTE.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), FurnaceTileEntitySnapshot.class.getSimpleName());
        }
        LiveTileEntity lte = opTE.get();
        if(!(lte instanceof LiveFurnaceTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), FurnaceTileEntitySnapshot.class.getSimpleName());
        }
        LiveFurnaceTileEntity lfte = (LiveFurnaceTileEntity)lte;
        this.inventory.apply(lfte.getInventory());
        return this;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return Arrays.asList(BlockTypes.FURNACE.get());
    }

    @Override
    public FurnaceInventorySnapshot getInventory() {
        return this.inventory;
    }

    @Override
    public FurnaceTileEntitySnapshot getSnapshot() {
        return new BFurnaceEntitySnapshot(this);
    }
}

