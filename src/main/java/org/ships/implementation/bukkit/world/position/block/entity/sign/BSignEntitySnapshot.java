package org.ships.implementation.bukkit.world.position.block.entity.sign;

import org.core.exceptions.BlockNotSupported;
import org.core.text.Text;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.sign.SignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

import java.util.Collection;

public class BSignEntitySnapshot implements SignTileEntitySnapshot {

    protected Text[] lines;

    public BSignEntitySnapshot(SignTileEntity entity){
        this(entity.getLines());
    }

    public BSignEntitySnapshot(Text... lines) {
        this.lines = lines.clone();
    }

    @Override
    public Text[] getLines() {
        return lines;
    }

    @Override
    public SignTileEntitySnapshot setLines(Text... lines) throws IndexOutOfBoundsException {
        if(lines.length > 4){
            throw new IndexOutOfBoundsException();
        }
        this.lines = lines.clone();
        return this;
    }

    @Override
    public SignTileEntity apply(BlockPosition position) throws BlockNotSupported {
        org.bukkit.block.BlockState state = ((BBlockPosition)position).getBukkitBlock().getState();
        if(!(state instanceof org.bukkit.block.Sign)){
            throw new BlockNotSupported(position.getBlockType(), "SignEntitySnapshot");
        }
        BSignEntity sign = new BSignEntity((org.bukkit.block.Sign)state);
        sign.setLines(this.lines);
        return sign;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return BlockTypes.OAK_SIGN.get().getLike();
    }

    @Override
    public SignTileEntitySnapshot getSnapshot() {
        return new BSignEntitySnapshot(this);
    }
}
