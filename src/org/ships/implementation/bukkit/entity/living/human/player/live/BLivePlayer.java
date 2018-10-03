package org.ships.implementation.bukkit.entity.living.human.player.live;

import org.core.entity.living.human.AbstractHuman;
import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.inventory.inventories.PlayerInventory;
import org.ships.implementation.bukkit.entity.BLiveEntity;
import org.ships.implementation.bukkit.entity.living.human.player.snapshot.BPlayerSnapshot;

public class BLivePlayer extends BLiveEntity<org.bukkit.entity.Player> implements LivePlayer {

    public BLivePlayer(org.bukkit.entity.Player entity) {
        super(entity);
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof BLivePlayer)){
            return false;
        }
        BLivePlayer player2 = (BLivePlayer)object;
        if(player2.getBukkitEntity().equals(this.getBukkitEntity())){
            return true;
        }
        return false;
    }

    @Override
    public boolean isViewingInventory() {
        return false;
    }

    @Override
    public PlayerInventory getInventory() {
        return null;
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public double getExhaustionLevel() {
        return 0;
    }

    @Override
    public double getSaturationLevel() {
        return 0;
    }

    @Override
    public AbstractHuman setFood(int value) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public AbstractHuman setExhaustionLevel(double value) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public AbstractHuman setSaturationLevel(double value) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public PlayerSnapshot createSnapshot() {
        return new BPlayerSnapshot(this);
    }
}
