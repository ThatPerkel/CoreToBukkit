package org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed;

import org.bukkit.block.data.BlockData;
import org.core.CorePlugin;
import org.core.world.direction.Direction;
import org.core.world.position.block.BlockType;
import org.core.world.position.block.details.data.keyed.AttachableKeyedData;
import org.ships.implementation.bukkit.utils.DirectionUtils;
import org.ships.implementation.bukkit.world.position.block.details.blocks.IBBlockDetails;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.attachableworkarounds.AttachableWorkAround1D14;
import org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed.attachableworkarounds.CommonAttachableWorkAround;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class BAttachableKeyedData implements AttachableKeyedData {

    public interface AttachableBlockWorkAround {

        Function<BlockData, Direction> GET_DIRECTION_FROM_BLOOCK_DATA = b -> DirectionUtils.toDirection(((org.bukkit.block.data.Directional)b).getFacing()).getOpposite();
        Consumer<Map.Entry<BlockData, Direction>> SET_BLOCK_DATA_FROM_DIRECTION = e -> ((org.bukkit.block.data.Directional) e.getKey()).setFacing(DirectionUtils.toFace(e.getValue().getOpposite()));

        Collection<BlockType> getTypes();
        Direction getAttachedDirection(BlockData data);
        org.bukkit.block.data.BlockData setAttachedDirection(org.bukkit.block.data.BlockData data, Direction direction);
    }

    public static Set<AttachableBlockWorkAround> workArounds = new HashSet<>();

    static {
        workArounds.addAll(Arrays.asList(CommonAttachableWorkAround.values()));
        int[] mcVersion = CorePlugin.getPlatform().getMinecraftVersion();
        if(mcVersion[1] >= 14){
            workArounds.addAll(Arrays.asList(AttachableWorkAround1D14.values()));
        }
    }

    private IBBlockDetails details;
    private AttachableBlockWorkAround workAround;

    private BAttachableKeyedData(IBBlockDetails details, AttachableBlockWorkAround workAround){
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

    public static Optional<BAttachableKeyedData> getKeyedData(IBBlockDetails details){
        Optional<AttachableBlockWorkAround> opWork = workArounds.stream().filter(w -> w.getTypes().stream().anyMatch(b -> details.getType().equals(b))).findAny();
        if(opWork.isPresent()){
            return Optional.of(new BAttachableKeyedData(details, opWork.get()));
        }
        return Optional.empty();
    }
}
