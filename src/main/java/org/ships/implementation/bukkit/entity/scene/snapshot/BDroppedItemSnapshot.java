package org.ships.implementation.bukkit.entity.scene.snapshot;

import org.core.entity.scene.droppeditem.DroppedItem;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.entity.scene.droppeditem.LiveDroppedItem;
import org.core.inventory.parts.snapshot.SlotSnapshot;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.scene.live.BLiveDroppedItem;
import org.ships.implementation.bukkit.inventory.item.BItemStack;
import org.ships.implementation.bukkit.world.position.BExactPosition;

import java.util.concurrent.TimeUnit;

public class BDroppedItemSnapshot extends BEntitySnapshot<LiveDroppedItem> implements DroppedItemSnapshot {

    protected int pickupDelay;
    protected SlotSnapshot slot;

    public BDroppedItemSnapshot(DroppedItem item){
        this(item.getPosition());
        this.pickupDelay = item.getPickupDelayTicks();
    }

    public BDroppedItemSnapshot(ExactPosition position) {
        super(position);
    }

    @Override
    public LiveDroppedItem spawnEntity() {
        org.bukkit.Location loc = ((BExactPosition)this.position).getBukkitLocation();
        loc.setPitch((float)this.pitch);
        loc.setYaw((float)this.yaw);
        org.bukkit.entity.Item item = (org.bukkit.entity.Item)loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.DROPPED_ITEM);
        item.setPickupDelay(this.pickupDelay);
        this.slot.getItem().ifPresent(i -> item.setItemStack(((BItemStack)i).getBukkitItem()));
        return new BLiveDroppedItem(item);
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
