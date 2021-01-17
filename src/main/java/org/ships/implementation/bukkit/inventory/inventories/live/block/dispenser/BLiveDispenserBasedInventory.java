package org.ships.implementation.bukkit.inventory.inventories.live.block.dispenser;

import org.bukkit.block.Container;
import org.core.inventory.inventories.live.block.dispenser.LiveDispenserBasedInventory;
import org.core.inventory.parts.Grid3x3;
import org.core.inventory.parts.Slot;
import org.core.world.position.block.BlockType;
import org.core.world.position.impl.sync.SyncBlockPosition;
import org.ships.implementation.bukkit.inventory.part.dispenser.DispenserBasedGrid;
import org.ships.implementation.bukkit.world.position.block.BBlockType;
import org.ships.implementation.bukkit.world.position.impl.sync.BBlockPosition;

import java.util.Optional;
import java.util.Set;

public abstract class BLiveDispenserBasedInventory implements LiveDispenserBasedInventory {

    protected abstract org.bukkit.block.Container getBukkitBlockState();
    protected DispenserBasedGrid grid = new DispenserBasedGrid() {
        @Override
        protected Container getContainer() {
            return BLiveDispenserBasedInventory.this.getBukkitBlockState();
        }
    };

    @Override
    public Grid3x3 getItems() {
        return this.grid;
    }

    @Override
    public BlockType[] getAllowedBlockType() {
        return new BlockType[]{new BBlockType(getBukkitBlockState().getType())};
    }

    @Override
    public SyncBlockPosition getPosition() {
        return new BBlockPosition(getBukkitBlockState().getBlock());
    }

    @Override
    public Set<Slot> getSlots() {
        return getItems().getSlots();
    }

    @Override
    public Optional<Slot> getSlot(int slotPos) {
        return this.getSlots().stream().filter(s -> s.getPosition().isPresent()).filter(s -> s.getPosition().get() == slotPos).findAny();
    }
}
