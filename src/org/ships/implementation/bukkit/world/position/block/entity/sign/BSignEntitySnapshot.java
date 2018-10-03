package org.ships.implementation.bukkit.world.position.block.entity.sign;

import org.core.exceptions.BlockNotSupported;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.sign.SignTileEntity;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.BBlockPosition;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractTileEntitySnapshot;

public class BSignEntitySnapshot extends AbstractTileEntitySnapshot<SignTileEntity> implements SignTileEntitySnapshot {

    protected String[] lines = new String[4];

    public BSignEntitySnapshot(BlockPosition position) {
        super(position);
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
    public BSignEntity apply() throws BlockNotSupported {
        BBlockPosition position = (BBlockPosition) getPosition();
        org.bukkit.block.BlockState state = position.getBukkitBlock().getState();
        if(!(state instanceof org.bukkit.block.Sign)){
            throw new BlockNotSupported(position.getBlockType(), "SignTileEntity");
        }
        org.bukkit.block.Sign sign = (org.bukkit.block.Sign)state;
        BSignEntity signEntity = new BSignEntity(sign);
        signEntity.setLines(this.getLines());
        return signEntity;
    }
}
