package org.ships.implementation.bukkit.world.position.block.entity.unknown;

import org.core.inventory.inventories.general.block.UnknownBlockAttachedInventory;
import org.core.world.position.block.entity.container.unknown.LiveUnknownContainerTileEntity;
import org.core.world.position.block.entity.container.unknown.UnknownContainerTileEntitySnapshot;
import org.ships.implementation.bukkit.inventory.inventories.live.block.BLiveUnknownBlockAttachedInventory;
import org.ships.implementation.bukkit.world.position.block.entity.AbstractLiveTileEntity;

public class BLiveUnknownContainerTileEntity extends AbstractLiveTileEntity implements LiveUnknownContainerTileEntity {

    public BLiveUnknownContainerTileEntity(org.bukkit.block.Container state) {
        super(state);
    }

    @Override
    public UnknownContainerTileEntitySnapshot getSnapshot() {
        return new BUnknownContainerTileEntitySnapshot(this);
    }

    @Override
    public UnknownBlockAttachedInventory getInventory() {
        return new BLiveUnknownBlockAttachedInventory(((org.bukkit.block.Container)this.state));
    }
}
