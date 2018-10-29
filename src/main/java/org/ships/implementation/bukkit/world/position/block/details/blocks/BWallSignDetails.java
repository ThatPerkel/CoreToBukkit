package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.core.exceptions.DirectionNotSupported;
import org.core.world.direction.Direction;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.blocks.WallSign;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.utils.DirectionUtils;
import org.ships.implementation.bukkit.world.position.block.entity.sign.BSignEntitySnapshot;

public class BWallSignDetails extends AbstractBlockDetails implements WallSign {

    protected SignTileEntitySnapshot snapshot;

    @Deprecated
    public BWallSignDetails(org.bukkit.block.data.BlockData data){
        this((org.bukkit.block.data.type.WallSign)data);
    }

    public BWallSignDetails(org.bukkit.block.data.type.WallSign wallsign){
        this(wallsign, new BSignEntitySnapshot());
    }

    public BWallSignDetails(org.bukkit.block.data.type.WallSign wallsign, SignTileEntitySnapshot snapshot){
        super(wallsign);
        this.snapshot = snapshot;
    }

    public org.bukkit.block.data.type.WallSign getBukkitData(){
        return (org.bukkit.block.data.type.WallSign)super.getBukkitData();
    }

    @Override
    public Direction getAttachedDirection() {
        return DirectionUtils.toDirection(getBukkitData().getFacing());
    }

    @Override
    public WallSign setAttachedDirection(Direction direction) throws DirectionNotSupported {
        if(!this.canFace(direction)){
            throw new DirectionNotSupported(direction, "WallSign");
        }
        getBukkitData().setFacing(DirectionUtils.toFace(direction));
        return this;
    }

    @Override
    public SignTileEntitySnapshot getTileEntity() {
        return this.snapshot;
    }

    @Override
    public void setTileEntity(SignTileEntitySnapshot tile) {
        this.snapshot = tile;
    }

    @Override
    public BlockType getType() {
        return BlockTypes.WALL_SIGN;
    }

    @Override
    public BlockDetails createCopyOf() {
        return new BWallSignDetails((org.bukkit.block.data.type.WallSign)getBukkitData().clone(), this.snapshot.getSnapshot());
    }
}
