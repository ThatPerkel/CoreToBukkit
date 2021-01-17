package org.ships.implementation.bukkit.world.position.impl.async;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.core.entity.living.human.player.LivePlayer;
import org.core.platform.Plugin;
import org.core.threadsafe.FutureResult;
import org.core.vector.type.Vector3;
import org.core.world.WorldExtent;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.flags.PositionFlag;
import org.core.world.position.impl.BlockPosition;
import org.core.world.position.impl.async.ASyncBlockPosition;
import org.core.world.position.impl.async.ASyncExactPosition;
import org.core.world.position.impl.sync.SyncExactPosition;
import org.core.world.position.impl.sync.SyncPosition;
import org.ships.implementation.bukkit.world.BWorldExtent;
import org.ships.implementation.bukkit.world.position.impl.BAbstractPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BExactPosition;

import java.util.Optional;

public class BAsyncExactPosition extends BAbstractPosition<Double> implements ASyncExactPosition {

    private final Location location;

    public BAsyncExactPosition(World world, double x, double y, double z){
        this(new Location(world, x, y, z));
    }

    public BAsyncExactPosition(Location location){
        this.location = location;
    }

    @Override
    public Vector3<Integer> getChunkPosition() {
        Chunk chunk = this.location.getChunk();
        return Vector3.valueOf(chunk.getX(), 0, chunk.getZ());
    }

    @Override
    public WorldExtent getWorld() {
        return new BWorldExtent(this.location.getWorld());
    }

    @Override
    public BlockSnapshot<? extends BlockPosition> getBlockDetails() {
        return this.toBlockPosition().getBlockDetails();
    }

    @Override
    public ASyncBlockPosition toBlockPosition() {
        return new BAsyncBlockPosition(this.location.getBlock());
    }

    @Override
    public Vector3<Double> getPosition() {
        return Vector3.valueOf(this.location.getX(), this.location.getY(), this.location.getZ());
    }

    @Override
    public FutureResult<SyncPosition<Double>> scheduleBlock(Plugin plugin, BlockDetails details, PositionFlag.SetFlag... flags) {
        FutureResult<SyncPosition<Double>> future = new FutureResult<>();
        Bukkit.getScheduler().runTask((org.bukkit.plugin.Plugin)plugin.getLauncher(), () -> {
            SyncExactPosition position = new BExactPosition(this.location);
            position.setBlock(details, flags);
            future.run(position);
        });
        return future;
    }

    @Override
    public FutureResult<SyncPosition<Double>> scheduleBlock(Plugin plugin, BlockDetails details, LivePlayer... player) {
        FutureResult<SyncPosition<Double>> future = new FutureResult<>();
        Bukkit.getScheduler().runTask((org.bukkit.plugin.Plugin)plugin.getLauncher(), () -> {
            SyncExactPosition position = new BExactPosition(this.location);
            position.setBlock(details, player);
            future.run(position);
        });
        return future;
    }

    @Override
    public FutureResult<SyncPosition<Double>> scheduleReset(Plugin plugin, LivePlayer... player) {
        FutureResult<SyncPosition<Double>> future = new FutureResult<>();
        Bukkit.getScheduler().runTask((org.bukkit.plugin.Plugin)plugin.getLauncher(), () -> {
            SyncExactPosition position = new BExactPosition(this.location);
            position.resetBlock(player);
            future.run(position);
        });
        return future;
    }

    @Override
    public FutureResult<LiveTileEntity> getTileEntity(Plugin plugin) {
        FutureResult<LiveTileEntity> future = new FutureResult<>();
        Bukkit.getScheduler().runTask((org.bukkit.plugin.Plugin)plugin.getLauncher(), () -> {
            SyncExactPosition position = new BExactPosition(this.location);
            Optional<LiveTileEntity> opEntity = position.getTileEntity();
            opEntity.ifPresent(future::run);
        });
        return future;
    }
}
