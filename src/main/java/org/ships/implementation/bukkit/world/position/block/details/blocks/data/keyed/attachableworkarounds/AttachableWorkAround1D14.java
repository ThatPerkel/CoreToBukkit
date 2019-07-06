package org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.attachableworkarounds;

import org.bukkit.block.data.BlockData;
import org.core.world.direction.Direction;
import org.core.world.direction.FourFacingDirection;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.blocktypes.post.BlockTypes1V14;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.BAttachableKeyedData;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public enum AttachableWorkAround1D14 implements BAttachableKeyedData.AttachableBlockWorkAround {

    LANTERN(d -> {
        try {
            return ((boolean)d.getClass().getMethod("isHanging").invoke(d)) ? FourFacingDirection.UP : FourFacingDirection.DOWN;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }, e -> {
        boolean hanging = e.getValue().equals(FourFacingDirection.DOWN) ? false : true;
        try {
            e.getKey().getClass().getMethod("setHanging", boolean.class).invoke(e.getKey(), hanging);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }, BlockTypes1V14.LANTERN.get());

    private Collection<BlockType> collection;
    private Function<BlockData, Direction> functionDirection;
    private Consumer<Map.Entry<BlockData, Direction>> consumer;

    AttachableWorkAround1D14(Function<BlockData, Direction> getDir, Consumer<Map.Entry<BlockData, Direction>> setDir, BlockType... types){
        this(getDir, setDir, Arrays.asList(types));
    }

    AttachableWorkAround1D14(Function<BlockData, Direction> getDir, Consumer<Map.Entry<BlockData, Direction>> setDir, Collection<BlockType> blockTypes){
        this.consumer = setDir;
        this.functionDirection = getDir;
        this.collection = blockTypes;
    }

    @Override
    public Collection<BlockType> getTypes() {
        return this.collection;
    }

    @Override
    public Direction getAttachedDirection(BlockData data) {
        return this.functionDirection.apply(data);
    }

    @Override
    public BlockData setAttachedDirection(BlockData data, Direction direction) {
        this.consumer.accept(new Map.Entry(){

            @Override
            public Object getKey() {
                return data;
            }

            @Override
            public Object getValue() {
                return direction;
            }

            @Deprecated
            @Override
            public Object setValue(Object o) {
                return o;
            }
        });
        return data;
    }
}
