package org.ships.implementation.bukkit.platform;

import org.bukkit.ChatColor;
import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.inventory.item.ItemTypes;
import org.core.platform.Platform;
import org.core.platform.Plugin;
import org.core.text.TextColour;
import org.core.text.TextColours;
import org.core.utils.Identifable;
import org.core.world.position.ExactPosition;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.entity.LiveTileEntity;
import org.ships.implementation.bukkit.entity.BEntityType;
import org.ships.implementation.bukkit.inventory.item.BItemType;
import org.ships.implementation.bukkit.text.BTextColour;
import org.ships.implementation.bukkit.world.position.block.BBlockType;
import org.ships.implementation.bukkit.world.position.block.details.blocks.BGeneralBlockDetails;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BukkitPlatform implements Platform {

    protected Set<EntityType<? extends Entity, ? extends EntitySnapshot<? extends Entity>>> entityTypes = new HashSet<>(Arrays.asList(new BEntityType.ZombieType()));
    protected Set<BlockDetails> modifiedBlockDetails = new HashSet<>();
    protected Map<Class<org.bukkit.entity.Entity>, Class<Entity>> entityToEntity = new HashMap<>();
    protected Map<Class<org.bukkit.block.BlockState>, Class<LiveTileEntity>> blockStateToTileEntity = new HashMap<>();
    protected Map<Class<org.bukkit.block.data.BlockData>, Class<BlockDetails>> blockDataToBlockDetails = new HashMap<>();

    public <E extends Entity, S extends EntitySnapshot<E>> Optional<S> createSnapshot(EntityType<E, S> type, ExactPosition position){
        if(type.equals(EntityTypes.PLAYER) || type.equals(EntityTypes.HUMAN)){
            return Optional.empty();
        }
        try {
            return Optional.of(type.getSnapshotClass().getConstructor(ExactPosition.class).newInstance(position));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Entity createEntityInstance(org.bukkit.entity.Entity entity){
        Optional<Map.Entry<Class<org.bukkit.entity.Entity>, Class<Entity>>> opEntry = entityToEntity.entrySet().stream().filter(e -> e.getKey().isInstance(entity)).findAny();
        if(!opEntry.isPresent()){
            System.err.println("entity convection not found: " + entity.getClass().getName());
            return null;
        }
        Class<Entity> bdclass = opEntry.get().getValue();
        try {
            return bdclass.getConstructor(org.bukkit.entity.Entity.class).newInstance(entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<LiveTileEntity> createTileEntityInstance(org.bukkit.block.BlockState state) {
        Optional<Map.Entry<Class<org.bukkit.block.BlockState>, Class<LiveTileEntity>>> opEntry = blockStateToTileEntity.entrySet().stream().filter(e -> e.getKey().isInstance(state)).findAny();
        if(!opEntry.isPresent()){
            return Optional.empty();
        }
        Class<LiveTileEntity> bdclass = opEntry.get().getValue();
        try {
            return Optional.of(bdclass.getConstructor(org.bukkit.block.BlockState.class).newInstance(state));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public BlockDetails createBlockDetailInstance(org.bukkit.block.data.BlockData data){
        Optional<Map.Entry<Class<org.bukkit.block.data.BlockData>, Class<BlockDetails>>> opEntry = this.blockDataToBlockDetails.entrySet().stream().filter(e -> e.getKey().isInstance(data)).findAny();
        if(!opEntry.isPresent()){
            return new BGeneralBlockDetails(data);
        }
        Class<BlockDetails> bdclass = opEntry.get().getValue();
        try {
            return bdclass.getConstructor(org.bukkit.block.data.BlockData.class).newInstance(data);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BlockDetails getBlockDetails(BlockType type){
        return modifiedBlockDetails.stream().filter(d -> d.getType().equals(type)).findAny().orElse(new BGeneralBlockDetails(((BBlockType)type).getBukkitMaterial().createBlockData()));
    }

    public BlockType getBlock(org.bukkit.Material material){
        return new BBlockType(material);
    }

    private org.bukkit.Material getMaterial(String id){
        String name = id.substring(10);
        for(org.bukkit.Material material : org.bukkit.Material.values()){
            if(material.name().toLowerCase().equals(name)){
                return material;
            }
        }
        return null;
    }

    @Override
    public int[] getMinecraftVersion() {
        return new int[]{1, 13, 1};
    }

    @Override
    public Set<Plugin> getPlugins() {
        return null;
    }

    @Override
    public <T extends Identifable> T get(BlockTypes blockId) {
        return (T)new BBlockType(getMaterial(blockId.getId()));
    }

    @Override
    public <T extends Identifable> T get(ItemTypes itemId) {
        return (T)new BItemType(getMaterial(itemId.getId()));
    }

    @Override
    public <T extends Identifable, E extends Entity, S extends EntitySnapshot<E>> T get(EntityTypes<E, S> entityId) {
        return (T)this.entityTypes.stream().filter(t -> t.getId().equals(entityId.getId())).findAny().get();
    }

    @Override
    public TextColour get(TextColours id) {
        for(ChatColor color : ChatColor.values()){
            if(id.equals("minecraft:" + color.name().toLowerCase())){
                return new BTextColour(color);
            }
        }
        return null;
    }
}
