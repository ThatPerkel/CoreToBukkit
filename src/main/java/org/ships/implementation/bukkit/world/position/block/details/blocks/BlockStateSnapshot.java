package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.bukkit.block.data.BlockData;
import org.core.CorePlugin;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.block.details.data.DirectionalData;
import org.core.world.position.block.details.data.keyed.*;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.TileEntity;
import org.core.world.position.block.entity.TileEntitySnapshot;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.position.BBlockPosition;
import org.ships.implementation.bukkit.world.position.block.BBlockType;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.BDirectionalData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.BRotationalData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BAttachableKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BMultiDirectionalKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BOpenableKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BWaterLoggedKeyedData;

import java.util.Optional;

public class BlockStateSnapshot implements BlockSnapshot, IBBlockDetails {

    private class BTileEntityKeyedData implements TileEntityKeyedData{

        @Override
        public Optional<TileEntitySnapshot<? extends TileEntity>> getData() {
            BukkitPlatform platform = (BukkitPlatform)CorePlugin.getPlatform();
            Optional<LiveTileEntity> opTile = platform.createTileEntityInstance(BlockStateSnapshot.this.state);
            if(!opTile.isPresent()){
                return Optional.empty();
            }
            return Optional.of(opTile.get().getSnapshot());
        }

        @Override
        public void setData(TileEntitySnapshot<? extends TileEntity> value) {
            BukkitPlatform platform = (BukkitPlatform)CorePlugin.getPlatform();
            Optional<LiveTileEntity> opTile = platform.createTileEntityInstance(BlockStateSnapshot.this.state);
            if(!opTile.isPresent()){
                return;
            }
            apply(value, opTile.get());
        }

        private <T extends LiveTileEntity> void apply(TileEntitySnapshot<T> snapshot, LiveTileEntity lte){
            snapshot.apply((T)lte);
        }
    }

    protected org.bukkit.block.BlockState state;

    public BlockStateSnapshot(org.bukkit.block.BlockState state){
        this.state = state;
    }

    public org.bukkit.block.BlockState getBukkitBlockState(){
        return this.state;
    }

    @Override
    public BlockPosition getPosition() {
        return new BBlockPosition(this.state.getBlock());
    }

    @Override
    public BlockType getType() {
        return new BBlockType(this.state.getType());
    }

    @Override
    public BlockSnapshot createSnapshot(BlockPosition position) {
        return new BExtendedBlockSnapshot(position, this.state.getBlockData());
    }

    @Override
    public Optional<DirectionalData> getDirectionalData() {
        if(this.state.getBlockData() instanceof org.bukkit.block.data.Directional){
            return Optional.of(new BDirectionalData((org.bukkit.block.data.Directional)this.state.getBlockData()));
        }
        if(this.state.getBlockData() instanceof org.bukkit.block.data.Rotatable){
            return Optional.of(new BRotationalData((org.bukkit.block.data.Rotatable)this.state.getBlockData()));
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> get(Class<? extends KeyedData<T>> data) {
        Optional<KeyedData<T>> opKey = getKey(data);
        if(opKey.isPresent()){
            return opKey.get().getData();
        }
        return Optional.empty();
    }

    @Override
    public <T> BlockDetails set(Class<? extends KeyedData<T>> data, T value) {
        Optional<KeyedData<T>> opKey = getKey(data);
        opKey.ifPresent(k -> k.setData(value));
        return this;
    }

    @Override
    public BlockSnapshot createCopyOf() {
        return createSnapshot(this.getPosition());
    }

    @Override
    public BlockData getBukkitData() {
        return this.state.getBlockData();
    }

    @Override
    public void setBukkitData(BlockData data) {
        this.state.setBlockData(data);
    }

    private <T> Optional<KeyedData<T>> getKey(Class<? extends KeyedData<T>> data){
        KeyedData<T> key = null;
        if(data.isAssignableFrom(WaterLoggedKeyedData.class) && this.state.getBlockData() instanceof org.bukkit.block.data.Waterlogged){
            key = (KeyedData<T>) new BWaterLoggedKeyedData((org.bukkit.block.data.Waterlogged)this.state.getBlockData());
        }else if(data.isAssignableFrom(TileEntityKeyedData.class)){
            key = (KeyedData<T>) new BlockStateSnapshot.BTileEntityKeyedData();
        }else if(data.isAssignableFrom(OpenableKeyedData.class) && (this.state.getBlockData() instanceof org.bukkit.block.data.Openable)){
            key = (KeyedData<T>) new BOpenableKeyedData((org.bukkit.block.data.Openable)this.state.getBlockData());
        }else if(data.isAssignableFrom(AttachableKeyedData.class) && BAttachableKeyedData.getKeyedData(this).isPresent()){
            key = (KeyedData<T>) BAttachableKeyedData.getKeyedData(this).get();
        }else if(data.isAssignableFrom(MultiDirectionalKeyedData.class) && this.state.getBlockData() instanceof org.bukkit.block.data.MultipleFacing){
            key = (KeyedData<T>) new BMultiDirectionalKeyedData((org.bukkit.block.data.MultipleFacing)this.state.getBlockData());
        }
        return Optional.ofNullable(key);
    }
}
