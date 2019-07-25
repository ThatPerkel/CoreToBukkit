package org.ships.implementation.bukkit.world.position;

import org.core.CorePlugin;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.LiveEntity;
import org.core.entity.living.human.player.LivePlayer;
import org.core.exceptions.BlockNotSupported;
import org.core.text.Text;
import org.core.vector.types.Vector3Int;
import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.Position;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.data.keyed.KeyedData;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.TileEntity;
import org.core.world.position.block.entity.TileEntitySnapshot;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.human.player.live.BLivePlayer;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.text.BText;
import org.ships.implementation.bukkit.world.BWorldExtent;
import org.ships.implementation.bukkit.world.position.block.details.blocks.BBlockDetails;

import java.util.Optional;
import java.util.stream.Stream;

public class BBlockPosition implements BlockPosition {

    protected org.bukkit.block.Block block;

    public BBlockPosition(int x, int y, int z, org.bukkit.World world){
        this(world.getBlockAt(x, y, z));
    }

    public BBlockPosition(org.bukkit.block.Block block){
        if(block == null){
            new NullPointerException().printStackTrace();
        }
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
        return new BBlockDetails(this);
    }

    @Override
    public Position<Integer> setBlock(BlockDetails details) {
        this.block.setBlockData(((BBlockDetails)details).getBukkitData(), false);
        Optional<TileEntitySnapshot<? extends TileEntity>> opTile = details.get(KeyedData.TILED_ENTITY);
        if(opTile.isPresent()){
            try {
                opTile.get().apply(this);
            } catch (BlockNotSupported blockNotSupported) {
                blockNotSupported.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public Position<Integer> setBlock(BlockDetails details, LivePlayer... player) {
        Stream.of(player).forEach(lp -> ((BLivePlayer)lp).getBukkitEntity().sendBlockChange(this.block.getLocation(), ((BBlockDetails)details).getBukkitData()));
        Optional<TileEntitySnapshot<? extends TileEntity>> opTile = details.get(KeyedData.TILED_ENTITY);
        if(opTile.isPresent()){
            TileEntitySnapshot<? extends TileEntity> tile = opTile.get();
            if(tile instanceof SignTileEntitySnapshot){
                SignTileEntitySnapshot stes = (SignTileEntitySnapshot)tile;
                Text[] text = stes.getLines();
                String[] lines = new String[text.length];
                for(int A = 0; A < text.length; A++){
                    lines[A] = ((BText)text[A]).toBukkitString();
                }
                Stream.of(player).forEach(lp -> ((BLivePlayer)lp).getBukkitEntity().sendSignChange(this.block.getLocation(), lines));
            }
        }
        return this;
    }

    @Override
    public Position<Integer> resetBlock(LivePlayer... player) {
        return setBlock(getBlockDetails(), player);
    }

    @Override
    public Optional<LiveTileEntity> getTileEntity() {
        BukkitPlatform platform = (BukkitPlatform)CorePlugin.getPlatform();
        return platform.createTileEntityInstance(getBukkitBlock().getState());
    }

    @Override
    public <E extends LiveEntity, S extends EntitySnapshot<E>> Optional<S> createEntity(EntityType<E, S> type) {
        return ((BukkitPlatform)CorePlugin.getPlatform()).createSnapshot(type, this.toExactPosition());
    }

    @Override
    public boolean equals(Object value){
        if(!(value instanceof Position)){
            return false;
        }
        Position<? extends Number> pos = (Position<? extends Number>)value;
        return pos.getPosition().equals(getPosition());
    }
}
