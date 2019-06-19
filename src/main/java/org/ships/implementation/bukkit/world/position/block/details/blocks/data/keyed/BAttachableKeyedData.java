package org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed;

import org.bukkit.block.data.BlockData;
import org.core.world.direction.Direction;
import org.core.world.direction.FourFacingDirection;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.data.keyed.AttachableKeyedData;
import org.ships.implementation.bukkit.world.position.block.details.blocks.BBlockDetails;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class BAttachableKeyedData implements AttachableKeyedData {

    public interface AttachableBlockWorkAround {

        enum CommonWorkArounds implements AttachableBlockWorkAround{
            CARPET(b -> FourFacingDirection.DOWN, e -> {}, BlockTypes.BLACK_CARPET.get().getLike()),
            FIRE(b -> FourFacingDirection.DOWN, e -> {}, BlockTypes.FIRE.get());

            private Collection<BlockType> collection;
            private Function<BlockData, Direction> functionDirection;
            private Consumer<Map.Entry<BlockData, Direction>> consumer;

            CommonWorkArounds(Function<BlockData, Direction> getDir, Consumer<Map.Entry<BlockData, Direction>> setDir, BlockType... types){
                this(getDir, setDir, Arrays.asList(types));
            }

            CommonWorkArounds(Function<BlockData, Direction> getDir, Consumer<Map.Entry<BlockData, Direction>> setDir, Collection<BlockType> blockTypes){
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

        Collection<BlockType> getTypes();
        Direction getAttachedDirection(BlockData data);
        org.bukkit.block.data.BlockData setAttachedDirection(org.bukkit.block.data.BlockData data, Direction direction);
    }

    public static Set<AttachableBlockWorkAround> workArounds = new HashSet<>(Arrays.asList(AttachableBlockWorkAround.CommonWorkArounds.values()));

    private BBlockDetails details;
    private AttachableBlockWorkAround workAround;

    private BAttachableKeyedData(BBlockDetails details, AttachableBlockWorkAround workAround){
        this.details = details;
        this.workAround = workAround;
    }

    @Override
    public Optional<Direction> getData() {
        return Optional.of(this.workAround.getAttachedDirection(this.details.getBukkitData()));
    }

    @Override
    public void setData(Direction value) {
        details.setBukkitData(this.workAround.setAttachedDirection(details.getBukkitData(), value));
    }

    public static Optional<BAttachableKeyedData> getKeyedData(BBlockDetails details){
        Optional<AttachableBlockWorkAround> opWork = workArounds.stream().filter(w -> w.getTypes().stream().anyMatch(b -> details.getType().equals(b))).findAny();
        if(opWork.isPresent()){
            return Optional.of(new BAttachableKeyedData(details, opWork.get()));
        }
        return Optional.empty();
    }
}
