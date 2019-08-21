package org.ships.implementation.bukkit.entity.scene.snapshot;

import org.core.entity.scene.droppeditem.DroppedItem;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.entity.scene.droppeditem.LiveDroppedItem;
import org.core.inventory.parts.snapshot.SlotSnapshot;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.scene.live.BLiveDroppedItem;

import java.util.concurrent.TimeUnit;

public class BDroppedItemSnapshot extends BEntitySnapshot<LiveDroppedItem> implements DroppedItemSnapshot {

    protected int pickupDelay;
    protected SlotSnapshot slot = new SlotSnapshot(0, null);

    public BDroppedItemSnapshot(DroppedItem item){
        this(item.getPosition());
        this.pickupDelay = item.getPickupDelayTicks();
    }

    public BDroppedItemSnapshot(ExactPosition position) {
        super(position);
    }

    @Override
    public LiveDroppedItem spawnEntity() {
        BLiveDroppedItem item = new BLiveDroppedItem(this);
        item.setPickupDelay(this.pickupDelay);
        item.getHoldingItem().setItem(this.slot.getItem().orElse(null));
        return item;
    }

    @Override
    public int getPickupDelayTicks() {
        return this.pickupDelay;
    }

    @Override
    public DroppedItemSnapshot setPickupDelay(int ticks) {
        this.pickupDelay = ticks;
        return this;
    }

    @Override
    public DroppedItem setPickupDelay(int time, TimeUnit unit) {
        switch(unit){
            case NANOSECONDS:
                break;
            case MICROSECONDS:
                break;
            case MILLISECONDS:
                break;
            case SECONDS:
                this.pickupDelay = (time * 20);
                break;
            case MINUTES:
                this.pickupDelay = ((time * 20) * 60);
                break;
            case HOURS:
                break;
            case DAYS:
                break;
        }
        return this;
    }

    @Override
    public SlotSnapshot getHoldingItem() {
        return this.slot;
    }

    @Override
    public BDroppedItemSnapshot createSnapshot() {
        return new BDroppedItemSnapshot(this);
    }

}
