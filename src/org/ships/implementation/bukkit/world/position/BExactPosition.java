package org.ships.implementation.bukkit.world.position;

import org.bukkit.Location;
import org.core.CorePlugin;
import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.vector.types.Vector3Double;
import org.core.vector.types.Vector3Int;
import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.core.world.position.Position;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.BWorldExtent;
import org.ships.implementation.bukkit.world.position.block.details.blocks.AbstractBlockDetails;

import java.util.Optional;

public class BExactPosition implements ExactPosition {

    protected org.bukkit.Location location;

    public BExactPosition(double x, double y, double z, org.bukkit.World world){
        this(new Location(world, x, y, z));
    }

    public BExactPosition(org.bukkit.Location location){
        this.location = location;
    }

    public org.bukkit.Location getBukkitLocation(){
        return this.location;
    }

    @Override
    public Vector3Int getChunkPosition() {
        return new Vector3Int(this.location.getChunk().getX(), 0, this.location.getChunk().getZ());
    }

    @Override
    public Vector3Double getPosition() {
        return new Vector3Double(this.location.getX(), this.location.getY(), this.location.getZ());
    }

    @Override
    public BlockPosition toBlockPosition() {
        return new BBlockPosition(this.location.getBlock());
    }

    @Override
    public WorldExtent getWorld() {
        return new BWorldExtent(this.location.getWorld());
    }

    @Override
    public BlockDetails getBlockDetails() {
        return ((BukkitPlatform)CorePlugin.getPlatform()).createBlockDetailInstance(this.location.getBlock().getBlockData());
    }

    @Override
    public Position<Double> setBlock(BlockDetails details) {
        this.location.getBlock().setBlockData(((AbstractBlockDetails)details).getBukkitData());
        return this;
    }

    @Override
    public Optional<LiveTileEntity> getTileEntity() {
        return ((BukkitPlatform)CorePlugin.getPlatform()).createTileEntityInstance(this.location.getBlock().getState());
    }

    @Override
    public <E extends Entity, S extends EntitySnapshot<E>> Optional<S> createEntity(EntityType<E, S> type) {
        return ((BukkitPlatform) CorePlugin.getPlatform()).createSnapshot(type, this);
    }
}
