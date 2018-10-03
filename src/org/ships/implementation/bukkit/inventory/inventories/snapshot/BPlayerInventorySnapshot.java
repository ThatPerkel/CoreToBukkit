package org.ships.implementation.bukkit.inventory.inventories.snapshot;

import org.core.entity.living.human.player.Player;
import org.core.inventory.inventories.PlayerInventory;
import org.core.inventory.inventories.snapshots.PlayerInventorySnapshot;

public class BPlayerInventorySnapshot extends PlayerInventorySnapshot implements BEntityInventorySnapshot<Player> {

    public BPlayerInventorySnapshot(PlayerInventory inventory) {
        super(inventory);
    }

    @Override
    public PlayerInventorySnapshot createSnapshot() {
        return new BPlayerInventorySnapshot(this);
    }

    @Override
    public void apply(Player entity) {

    }
}
