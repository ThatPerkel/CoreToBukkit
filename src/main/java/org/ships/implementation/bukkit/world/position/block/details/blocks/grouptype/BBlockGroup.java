package org.ships.implementation.bukkit.world.position.block.details.blocks.grouptype;

import org.core.world.position.block.BlockType;
import org.core.world.position.block.grouptype.BlockGroup;

public class BBlockGroup implements BlockGroup {

    private BlockType[] type;
    private String name;

    public BBlockGroup(String name, BlockType... types){
        this.name = name;
        this.type = types;
    }

    @Override
    public BlockType[] getGrouped() {
        return this.type;
    }

    @Override
    public String getId() {
        return "minecraft." + this.name.toLowerCase();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
