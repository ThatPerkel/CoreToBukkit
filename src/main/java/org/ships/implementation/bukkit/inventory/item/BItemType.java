package org.ships.implementation.bukkit.inventory.item;

import org.core.inventory.item.ItemType;
import org.core.inventory.item.stack.ItemStackSnapshot;
import org.core.world.position.block.BlockType;
import org.ships.implementation.bukkit.inventory.item.stack.BItemStackSnapshot;
import org.ships.implementation.bukkit.world.position.block.BBlockType;

import java.util.Optional;

public class BItemType implements ItemType {

    protected org.bukkit.Material material;

    public BItemType(org.bukkit.Material material){
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
    public ItemStackSnapshot getDefaultItemStack() {
        org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(this.material, 1);
        return new BItemStackSnapshot(stack);
    }

    @Override
    public Optional<BlockType> getBlockType() {
        if (!this.material.isBlock()){
    return Optional.empty();
        }
        return Optional.of(new BBlockType(this.material));
    }

    @Override
    public String getId() {
        return this.material.getKey().toString();
    }

    @Override
    public String getName() {
        return this.material.name();
    }
}
