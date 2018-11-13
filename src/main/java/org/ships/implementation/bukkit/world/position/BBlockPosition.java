package org.ships.implementation.bukkit.world.position;

import org.core.CorePlugin;
import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.exceptions.BlockNotSupported;
import org.core.vector.types.Vector3Int;
import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.Position;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.TiledBlockDetails;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.BWorldExtent;
import org.ships.implementation.bukkit.world.position.block.details.blocks.AbstractBlockDetails;

import java.util.Optional;

public class BBlockPosition implements BlockPosition {

    protected org.bukkit.block.Block block;

    public BBlockPosition(int x, int y, int z, org.bukkit.World world) {
        this(world.getBlockAt(x, y, z));
    }

    public BBlockPosition(org.bukkit.block.Block block) {
        if (block == null) {
            new NullPointerException().printStackTrace();
        }
        this.block = block;
    }

    public org.bukkit.block.Block getBukkitBlock() {
        return this.block;
    }

    @Override
    public Vector3Int getChunkPosition() {
        return new Vector3Int(this.block.getChunk().getX(), 0, this.block.getChunk().getZ());
    }

    @Override
    public Vector3Int getPosition() {
        return new Vector3Int(this.block.getX(), this.block.getY(), this.block.getZ());
    }

    @Override
    public WorldExtent getWorld() {
        return new BWorldExtent(this.block.getWorld());
    }

    @Override
    public BlockDetails getBlockDetails() {
        BlockDetails bd = ((BukkitPlatform) CorePlugin.getPlatform()).createBlockDetailInstance(getBukkitBlock().getBlockData());
        if (bd instanceof TiledBlockDetails) {
            TiledBlockDetails tbd = (TiledBlockDetails) bd;
            getTileEntity().ifPresent(te -> {
                tbd.setTileEntity(te.getSnapshot());
            });
        }
        return bd;
    }

    @Override
    public Position<Integer> setBlock(BlockDetails details) {
        this.block.setBlockData(((AbstractBlockDetails) details).getBukkitData());
        if (details instanceof TiledBlockDetails) {
            TiledBlockDetails tbd = (TiledBlockDetails) details;
            try {
                tbd.getTileEntity().apply(this);
            } catch (BlockNotSupported blockNotSupported) {
                blockNotSupported.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public Optional<LiveTileEntity> getTileEntity() {
        BukkitPlatform platform = (BukkitPlatform) CorePlugin.getPlatform();
        Optional<LiveTileEntity> opTE = platform.createTileEntityInstance(getBukkitBlock().getState());
        return opTE;
    }


    @Override
    public <E extends Entity, S extends EntitySnapshot<E>> Optional<S> createEntity(EntityType<E, S> type) {
        return ((BukkitPlatform) CorePlugin.getPlatform()).createSnapshot(type, this.toExactPosition());
    }

    @Override
    public boolean equals(Object value) {
        if (!(value instanceof Position)) {
            return false;
        }
        Position<? extends Number> pos = (Position<? extends Number>) value;
        boolean check = pos.getPosition().equals(getPosition());
        return check;
    }
}
