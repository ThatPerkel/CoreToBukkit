package org.ships.implementation.bukkit.entity.scene.live;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.scene.droppeditem.DroppedItem;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.entity.scene.droppeditem.LiveDroppedItem;
import org.core.inventory.parts.Slot;
import org.ships.implementation.bukkit.entity.BLiveEntity;
import org.ships.implementation.bukkit.inventory.inventories.live.entity.BLiveDroppedItemSlot;

import java.util.concurrent.TimeUnit;

public class BLiveDroppedItem extends BLiveEntity<org.bukkit.entity.Item> implements LiveDroppedItem {

    @Deprecated
    public BLiveDroppedItem(Entity entity){
        this((Item)entity);
    }

    public BLiveDroppedItem(Item entity) {
        super(entity);
    }

    @Override
    public int getPickupDelayTicks() {
        return getBukkitEntity().getPickupDelay();
    }

    @Override
    public DroppedItem setPickupDelay(int ticks) {
        getBukkitEntity().setPickupDelay(ticks);
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
                getBukkitEntity().setPickupDelay(time * 20);
                break;
            case MINUTES:
                getBukkitEntity().setPickupDelay((time * 20) * 60);
                break;
            case HOURS:
                break;
            case DAYS:
                break;
        }
        return this;
    }

    @Override
    public Slot getHoldingItem() {
        return new BLiveDroppedItemSlot(getBukkitEntity());
    }

    @Override
    public EntityType<DroppedItem, DroppedItemSnapshot> getType() {
        return EntityTypes.DROPPED_ITEM;
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return null;
    }
}
