package org.ships.implementation.bukkit.world;

import org.bukkit.block.BlockState;
import org.core.CorePlugin;
import org.core.entity.LiveEntity;
import org.core.world.WorldExtent;
import org.core.world.position.impl.async.ASyncBlockPosition;
import org.core.world.position.impl.async.ASyncExactPosition;
import org.core.world.position.impl.sync.SyncBlockPosition;
import org.core.world.position.impl.sync.SyncExactPosition;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.position.impl.async.BAsyncBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.async.BAsyncExactPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BExactPosition;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BWorldExtent implements WorldExtent {

    protected org.bukkit.World world;

    public BWorldExtent(org.bukkit.World world){
        this.world = world;
    }

    public org.bukkit.World getBukkitWorld(){
        return this.world;
    }

    @Override
    public SyncExactPosition getPosition(double x, double y, double z) {
        return new BExactPosition(x, y, z, this.world);
    }

    @Override
    public ASyncExactPosition getAsyncPosition(double x, double y, double z) {
        return new BAsyncExactPosition(this.world, x, y, z);
    }

    @Override
    public SyncBlockPosition getPosition(int x, int y, int z) {
        return new BBlockPosition(x, y, z, this.world);
    }

    @Override
    public ASyncBlockPosition getAsyncPosition(int x, int y, int z) {
        return new BAsyncBlockPosition(this.world, x, y, z);
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public Set<LiveEntity> getEntities() {
        Set<LiveEntity> entities = new HashSet<>();
        this.world.getEntities().forEach(e -> {
            LiveEntity entity = ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(e);
            if(entity == null){
                return;
            }
            entities.add(entity);
        });
        return entities;
    }

    @Override
    public Set<LiveTileEntity> getTileEntities() {
        Set<LiveTileEntity> set = new HashSet<>();
        for (org.bukkit.Chunk chunk : this.world.getLoadedChunks()){
            for (BlockState state : chunk.getTileEntities()) {
                ((BukkitPlatform) CorePlugin.getPlatform()).createTileEntityInstance(state).ifPresent(set::add);
            }
        }
        return set;
    }

    @Override
    public String getName() {
        return this.world.getName();
    }

    @Override
    public UUID getUniquieId() {
        return this.world.getUID();
    }

    @Override
    public String getPlatformUniquieId() {
        return getName();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof WorldExtent)){
            return false;
        }
        return ((WorldExtent)obj).getName().equals(this.getName());
    }
}
