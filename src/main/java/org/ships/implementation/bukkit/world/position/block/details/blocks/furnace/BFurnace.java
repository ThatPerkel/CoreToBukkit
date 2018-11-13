package org.ships.implementation.bukkit.world.position.block.details.blocks.furnace;

import org.bukkit.block.data.type.Furnace;
import org.core.exceptions.DirectionNotSupported;
import org.core.world.direction.Direction;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.RotateDetails;
import org.core.world.position.block.details.blocks.furnace.GeneralFurnace;
import org.core.world.position.block.entity.container.furnace.FurnaceTileEntitySnapshot;
import org.ships.implementation.bukkit.utils.DirectionUtils;
import org.ships.implementation.bukkit.world.position.block.details.blocks.AbstractBlockDetails;
import org.ships.implementation.bukkit.world.position.block.entity.furnace.BFurnaceTileEntitySnapshot;

public class BFurnace extends AbstractBlockDetails implements GeneralFurnace {

    protected FurnaceTileEntitySnapshot snapshot;

    @Deprecated
    public BFurnace(org.bukkit.block.data.BlockData data){
        this((org.bukkit.block.data.type.Furnace)data);
    }

    public BFurnace(org.bukkit.block.data.type.Furnace furnace){
        this(furnace, new BFurnaceTileEntitySnapshot());
    }

    public BFurnace(org.bukkit.block.data.type.Furnace furnace, FurnaceTileEntitySnapshot ftes) {
        super(furnace);
        this.snapshot = ftes;
    }

    @Override
    public org.bukkit.block.data.type.Furnace getBukkitData(){
        return (Furnace) super.getBukkitData();
    }

    @Override
    public Direction getFacingDirection() {
        return DirectionUtils.toDirection(getBukkitData().getFacing());
    }

    @Override
    public RotateDetails setFacingDirection(Direction direction) throws DirectionNotSupported {
        getBukkitData().setFacing(DirectionUtils.toFace(direction));
        return this;
    }

    @Override
    public FurnaceTileEntitySnapshot getTileEntity() {
        return this.snapshot;
    }

    @Override
    public void setTileEntity(FurnaceTileEntitySnapshot tile) {
        this.snapshot = tile;
    }

    @Override
    public BlockDetails createCopyOf() {
        return new BFurnace((org.bukkit.block.data.type.Furnace) getBukkitData().clone(), this.snapshot.getSnapshot());
    }
}
