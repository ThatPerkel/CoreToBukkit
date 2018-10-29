package org.ships.implementation.bukkit.entity.living.human.player.live;

import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.human.AbstractHuman;
import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.Player;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.inventory.inventories.PlayerInventory;
import org.core.source.viewer.CommandViewer;
import org.ships.implementation.bukkit.entity.BLiveEntity;
import org.ships.implementation.bukkit.entity.living.human.player.snapshot.BPlayerSnapshot;
import org.ships.implementation.bukkit.inventory.inventories.live.BLivePlayerInventory;

public class BLivePlayer extends BLiveEntity<org.bukkit.entity.Player> implements LivePlayer {

    @Deprecated
    public BLivePlayer(org.bukkit.entity.Entity entity){
        this((org.bukkit.entity.Player)entity);
    }

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
        return getBukkitEntity().getOpenInventory() != null;
    }

    @Override
    public PlayerInventory getInventory() {
        return new BLivePlayerInventory(this);
    }

    @Override
    public int getFoodLevel() {
        return getBukkitEntity().getFoodLevel();
    }

    @Override
    public double getExhaustionLevel() {
        return getBukkitEntity().getExhaustion();
    }

    @Override
    public double getSaturationLevel() {
        return getBukkitEntity().getSaturation();
    }

    @Override
    public AbstractHuman setFood(int value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setFoodLevel(value);
        return this;
    }

    @Override
    public AbstractHuman setExhaustionLevel(double value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setExhaustion((float)value);
        return this;
    }

    @Override
    public AbstractHuman setSaturationLevel(double value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setSaturation((float)value);
        return this;
    }

    @Override
    public EntityType<Player, PlayerSnapshot> getType() {
        return EntityTypes.PLAYER;
    }

    @Override
    public PlayerSnapshot createSnapshot() {
        return new BPlayerSnapshot(this);
    }

    @Override
    public Player setGravity(boolean check) {
        getBukkitEntity().setGravity(check);
        return this;
    }

    @Override
    public boolean hasGravity() {
        return getBukkitEntity().hasGravity();
    }

    @Override
    public CommandViewer sendMessage(String message) {
        getBukkitEntity().sendMessage(message);
        return this;
    }

    @Override
    public CommandViewer sendMessagePlain(String message) {
        getBukkitEntity().sendMessage(org.bukkit.ChatColor.stripColor(message));
        return this;
    }
}
