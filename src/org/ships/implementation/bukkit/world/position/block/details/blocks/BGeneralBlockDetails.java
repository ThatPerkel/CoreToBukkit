package org.ships.implementation.bukkit.world.position.block.details.blocks;

import org.bukkit.block.data.BlockData;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.blocks.GeneralBlock;

public class BGeneralBlockDetails extends AbstractBlockDetails implements GeneralBlock {

    public BGeneralBlockDetails(BlockData blockData) {
        super(blockData);
    }

    @Override
    public BlockDetails createCopyOf() {
        return new BGeneralBlockDetails(this.blockData.clone());
    }
}
