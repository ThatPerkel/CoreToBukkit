package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.core.CorePlugin;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.BlockDetails;
import org.ships.implementation.bukkit.platform.BukkitPlatform;

public abstract class AbstractBlockDetails implements BlockDetails {

    protected org.bukkit.block.data.BlockData blockData;

    public AbstractBlockDetails(org.bukkit.block.data.BlockData blockData){
        this.blockData = blockData;
    }

    public org.bukkit.block.data.BlockData getBukkitData(){
        return this.blockData;
    }

    @Override
    public BlockType getType() {
        return ((BukkitPlatform)CorePlugin.getPlatform()).getBlock(this.blockData.getMaterial());
    }
}
