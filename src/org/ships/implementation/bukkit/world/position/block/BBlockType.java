package org.ships.implementation.bukkit.world.position.block;

import org.core.inventory.item.ItemType;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.BlockDetails;
import org.ships.implementation.bukkit.inventory.item.BItemType;

import java.util.Optional;
import java.util.Set;

public class BBlockType implements BlockType {

    protected org.bukkit.Material material;

    public BBlockType(org.bukkit.Material material){
        this.material = material;
    }

    public org.bukkit.Material getBukkitMaterial(){
        return this.material;
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof BBlockType){
            BBlockType type = (BBlockType) object;
            if(type.getBukkitMaterial().equals(this.material)){
                return true;
            }
        }
        if(object instanceof BItemType){
            BItemType type = (BItemType) object;
            if(type.getBukkitMaterial().equals(this.material)){
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockDetails getDefaultBlockDetails() {
        return null;
    }

    @Override
    public Set<BlockType> getLike() {
        return null;
    }

    @Override
    public Optional<ItemType> getItemType() {
        return Optional.empty();
    }

    @Override
    public String getId() {
        return "minecraft:" + getName().toLowerCase();
    }

    @Override
    public String getName() {
        return this.material.name();
    }
}
