package org.ships.implementation.bukkit.world.position.block.entity.sign;

import org.core.world.position.block.entity.sign.LiveSignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractLiveTileEntity;

public class BSignEntity extends AbstractLiveTileEntity implements LiveSignTileEntity {

    @Deprecated
    public BSignEntity(org.bukkit.block.BlockState state){
        this((org.bukkit.block.Sign)state);
    }

    public BSignEntity(org.bukkit.block.Sign state) {
        super(state);
    }

    public org.bukkit.block.Sign getBukkitSign(){
        return (org.bukkit.block.Sign)this.state;
    }

    @Override
    public String[] getLines() {
        String[] bLines = getBukkitSign().getLines();
        String[] lines = new String[4];
        for(int A = 0; A < bLines.length; A++){
            lines[A] = bLines[A];
        }
        return lines;
    }

    @Override
    public SignTileEntity setLines(String... lines) throws IndexOutOfBoundsException {
        for(int A = 0; A < lines.length; A++){
            getBukkitSign().setLine(A, lines[A]);
        }
        return this;
    }

    @Override
    public SignTileEntitySnapshot getSnapshot() {
        return new BSignEntitySnapshot(this);
    }
}
