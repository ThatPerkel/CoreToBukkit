package org.ships.implementation.bukkit.world.position.block.entity.sign;

import org.core.world.position.block.entity.sign.SignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractTileEntity;

public class BSignEntity extends AbstractTileEntity implements SignTileEntity {

    public BSignEntity(org.bukkit.block.Sign state) {
        super(state);
    }

    public org.bukkit.block.Sign getBukkitSign(){
        return (org.bukkit.block.Sign)this.state;
    }

    @Override
    public String[] getLines() {
        return getBukkitSign().getLines();
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
