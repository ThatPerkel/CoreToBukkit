package org.ships.implementation.bukkit.world.position;

import org.core.CorePlugin;
import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.vector.types.Vector3Int;
import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.Position;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.BWorldExtent;
import org.ships.implementation.bukkit.world.position.block.details.blocks.AbstractBlockDetails;

import java.util.Optional;

public class BBlockPosition implements BlockPosition {

    protected org.bukkit.block.Block block;

    public BBlockPosition(int x, int y, int z, org.bukkit.World world){
        this(world.getBlockAt(x, y, z));
    }

    public BBlockPosition(org.bukkit.block.Block block){
        this.block = block;
    }

    public org.bukkit.block.Block getBukkitBlock(){
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
        return ((BukkitPlatform)CorePlugin.getPlatform()).createBlockDetailInstance(getBukkitBlock().getBlockData());
    }

    @Override
    public Position<Integer> setBlock(BlockDetails details) {
        this.block.setBlockData(((AbstractBlockDetails)details).getBukkitData());
        return this;
    }

    @Override
    public Optional<LiveTileEntity> getTileEntity() {
        return ((BukkitPlatform)CorePlugin.getPlatform()).createTileEntityInstance(getBukkitBlock().getState());
    }

    @Override
    public <E extends Entity, S extends EntitySnapshot<E>> Optional<S> createEntity(EntityType<E, S> type) {
        return ((BukkitPlatform)CorePlugin.getPlatform()).createSnapshot(type, this.toExactPosition());
    }
}
