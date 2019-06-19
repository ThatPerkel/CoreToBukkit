package org.ships.implementation.bukkit.world.position.block.entity.banner;

import org.core.exceptions.BlockNotSupported;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.entity.LiveTileEntity;
import org.core.world.position.block.entity.banner.BannerTileEntity;
import org.core.world.position.block.entity.banner.BannerTileEntitySnapshot;
import org.core.world.position.block.entity.banner.LiveBannerTileEntity;
import org.core.world.position.block.entity.banner.pattern.PatternLayersSnapshot;
import org.ships.implementation.bukkit.world.position.block.entity.banner.pattern.BPatternLayersSnapshot;

import java.util.Collection;
import java.util.Optional;

public class BBannerTileEntitySnapshot implements BannerTileEntitySnapshot {

    protected PatternLayersSnapshot layers;

    public BBannerTileEntitySnapshot(){
        this.layers = new BPatternLayersSnapshot();
    }

    public BBannerTileEntitySnapshot(BannerTileEntity bannerTileEntity){
        this.layers = bannerTileEntity.getLayers().createSnapshot();
    }

    @Override
    public LiveBannerTileEntity apply(BlockPosition position) throws BlockNotSupported {
        Optional<LiveTileEntity> opTile = position.getTileEntity();
        if(!opTile.isPresent()){
            throw new BlockNotSupported(position.getBlockType(), "BannerTileEntity");
        }
        LiveTileEntity lte = opTile.get();
        if(!(lte instanceof BannerTileEntity)){
            throw new BlockNotSupported(position.getBlockType(), "BannerTileEntity");
        }
        LiveBannerTileEntity bte = (LiveBannerTileEntity) lte;
        bte.getLayers().removeLayers().addLayers(this.layers.getLayers());
        return bte;
    }

    @Override
    public Collection<BlockType> getSupportedBlocks() {
        return BlockTypes.BLACK_BANNER.get().getLike();
    }

    @Override
    public PatternLayersSnapshot getLayers() {
        return this.layers;
    }

    @Override
    public BannerTileEntitySnapshot getSnapshot() {
        return new BBannerTileEntitySnapshot(this);
    }
}
