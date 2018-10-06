package org.ships.implementation.bukkit.world.position.block.entity.sign;

import org.core.exceptions.BlockNotSupported;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.TileEntity;
import org.core.world.position.block.entity.TileEntitySnapshot;
import org.core.world.position.block.entity.sign.SignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.BBlockPosition;

public class BSignEntitySnapshot implements SignTileEntitySnapshot {

    protected String[] lines;

    public BSignEntitySnapshot(SignTileEntity entity){
        this(entity.getLines());
    }

    public BSignEntitySnapshot(String... lines) {
        this.lines = lines;
    }

    @Override
    public String[] getLines() {
        return lines;
    }

    @Override
    public SignTileEntitySnapshot setLines(String... lines) throws IndexOutOfBoundsException {
        if(lines.length > 4){
            throw new IndexOutOfBoundsException();
        }
        this.lines = lines;
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
    public TileEntitySnapshot<? extends TileEntity> getSnapshot() {
        return new BSignEntitySnapshot(this);
    }
}
