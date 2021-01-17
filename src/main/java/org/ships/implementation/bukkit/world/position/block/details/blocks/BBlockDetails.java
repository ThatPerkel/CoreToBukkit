package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.core.CorePlugin;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.block.details.data.DirectionalData;
import org.core.world.position.block.details.data.keyed.*;
import org.core.world.position.block.entity.TileEntity;
import org.core.world.position.block.entity.TileEntitySnapshot;
import org.core.world.position.impl.BlockPosition;
import org.core.world.position.impl.async.ASyncBlockPosition;
import org.core.world.position.impl.sync.SyncBlockPosition;
import org.ships.implementation.bukkit.world.position.block.BBlockType;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.BDirectionalData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.BRotationalData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BAttachableKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BMultiDirectionalKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BOpenableKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BWaterLoggedKeyedData;
import org.ships.implementation.bukkit.world.position.impl.async.BAsyncBlockPosition;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;

import java.util.Optional;

public class BBlockDetails implements BlockDetails, IBBlockDetails {

    protected class BTileEntityKeyedData implements TileEntityKeyedData{

        @Override
        public Optional<TileEntitySnapshot<? extends TileEntity>> getData() {
            return Optional.ofNullable(BBlockDetails.this.tileEntitySnapshot);
        }

        @Override
        public void setData(TileEntitySnapshot<? extends TileEntity> value) {
            BBlockDetails.this.tileEntitySnapshot = value;
        }
    }

    private org.bukkit.block.data.BlockData data;
    private TileEntitySnapshot<? extends TileEntity> tileEntitySnapshot;
    private final boolean async;

    public BBlockDetails(org.bukkit.block.data.BlockData data, boolean async){
        this.data = data;
        this.async = async;
        if(!async) {
            CorePlugin.getPlatform().getDefaultTileEntity(getType()).ifPresent(t -> tileEntitySnapshot = t);
        }
    }

    public BBlockDetails(BAsyncBlockPosition position){
        this(new BBlockPosition(position.getBukkitBlock()), true);
    }

    public BBlockDetails(BBlockPosition position){
        this(new BBlockPosition(position.getBukkitBlock()), false);
    }

    private BBlockDetails(BBlockPosition position, boolean async){
        this(position.getBukkitBlock().getBlockData(), async);
        if(!async && position.getTileEntity().isPresent()){
            this.tileEntitySnapshot = position.getTileEntity().get().getSnapshot();
        }
    }

    @Override
    public org.bukkit.block.data.BlockData getBukkitData(){
        return this.data;
    }

    @Override
    public void setBukkitData(org.bukkit.block.data.BlockData data){
        this.data = data;
    }

    @Override
    public BlockType getType() {
        return new BBlockType(this.data.getMaterial());
    }

    @Override
    public <T extends BlockPosition> BlockSnapshot<T> createSnapshot(T position) {
        if(position instanceof SyncBlockPosition){
            return (BlockSnapshot<T>) new BExtendedBlockSnapshot((SyncBlockPosition) position, this.getBukkitData());
        }
        return (BlockSnapshot<T>) new AsyncBlockStateSnapshot((ASyncBlockPosition) position, this.getBukkitData());
    }

    @Override
    public Optional<DirectionalData> getDirectionalData() {
        if(this.data instanceof org.bukkit.block.data.Directional){
            return Optional.of(new BDirectionalData((org.bukkit.block.data.Directional)this.data));
        }
        if(this.data instanceof org.bukkit.block.data.Rotatable){
            return Optional.of(new BRotationalData((org.bukkit.block.data.Rotatable)this.data));
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
    public boolean equals(Object obj){
        if(!(obj instanceof BBlockDetails)){
            return false;
        }
        BBlockDetails details = (BBlockDetails) obj;
        return details.data.equals(this.data);
    }

    protected <T> Optional<KeyedData<T>> getKey(Class<? extends KeyedData<T>> data){
        KeyedData<T> key = null;
        if(data.isAssignableFrom(WaterLoggedKeyedData.class) && this.data instanceof org.bukkit.block.data.Waterlogged){
            key = (KeyedData<T>) new BWaterLoggedKeyedData((org.bukkit.block.data.Waterlogged)this.data);
        }else if(data.isAssignableFrom(OpenableKeyedData.class) && (this.data instanceof org.bukkit.block.data.Openable)){
            key = (KeyedData<T>) new BOpenableKeyedData((org.bukkit.block.data.Openable)this.data);
        }else if(data.isAssignableFrom(AttachableKeyedData.class) && BAttachableKeyedData.getKeyedData(this).isPresent()){
            key = (KeyedData<T>) BAttachableKeyedData.getKeyedData(this).get();
        }else if(data.isAssignableFrom(MultiDirectionalKeyedData.class) && this.data instanceof org.bukkit.block.data.MultipleFacing){
            key = (KeyedData<T>) new BMultiDirectionalKeyedData((org.bukkit.block.data.MultipleFacing)this.data);
        }
        return Optional.ofNullable(key);
    }

    @Override
    public BlockDetails createCopyOf() {
        return new BBlockDetails(this.data.clone(), this.async);
    }
}
