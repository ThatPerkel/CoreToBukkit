package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.core.exceptions.DirectionNotSupported;
import org.core.world.direction.Direction;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.blocks.WallSign;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.utils.DirectionUtils;

public class BWallSignDetails implements WallSign {

    org.bukkit.block.data.type.WallSign wallsign;
    SignTileEntitySnapshot snapshot;

    public BWallSignDetails(org.bukkit.block.data.type.WallSign wallSign){
        this.wallsign = wallSign;
    }

    @Override
    public Direction getAttachedDirection() {
        return DirectionUtils.toDirection(this.wallsign.getFacing());
    }

    @Override
    public WallSign setAttachedDirection(Direction direction) throws DirectionNotSupported {
        if(!this.canFace(direction)){
            throw new DirectionNotSupported(direction, "WallSign");
        }
        this.wallsign.setFacing(DirectionUtils.toFace(direction));
        return this;
    }

    @Override
    public SignTileEntitySnapshot getTileEntity() {
        return this.snapshot;
    }

    @Override
    public BlockType getType() {
        return BlockTypes.WALL_SIGN;
    }
}
