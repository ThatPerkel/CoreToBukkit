package org.ships.implementation.bukkit.world.position.block;

import org.bukkit.Tag;
import org.core.CorePlugin;
import org.core.inventory.item.ItemType;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.BlockDetails;
import org.ships.implementation.bukkit.inventory.item.BItemType;
import org.ships.implementation.bukkit.platform.BukkitPlatform;

import java.lang.reflect.Field;
import java.util.HashSet;
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
        return ((BukkitPlatform) CorePlugin.getPlatform()).createBlockDetailInstance(this.material.createBlockData());
    }

    @Override
    public Set<BlockType> getLike() {
        Set<BlockType> set = new HashSet<>();
        Class<org.bukkit.Tag> classTag = org.bukkit.Tag.class;
        for (Field field : classTag.getFields()){
            if(!field.getType().isAssignableFrom(classTag)){
                continue;
            }
            try {
                org.bukkit.Tag<org.bukkit.Material> tag = (Tag) field.get(null);
                tag.getValues().stream().forEach(m -> {
                    if(m.isBlock()){
                        set.add(new BBlockType(m));
                    }
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return set;
    }

    @Override
    public Optional<ItemType> getItemType() {
        if (this.material.isItem()){
            return Optional.of(new BItemType(this.material));
        }
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
