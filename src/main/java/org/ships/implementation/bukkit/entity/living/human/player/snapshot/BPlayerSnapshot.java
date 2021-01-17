package org.ships.implementation.bukkit.entity.living.human.player.snapshot;

import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.inventory.inventories.general.entity.PlayerInventory;
import org.core.inventory.inventories.snapshots.entity.PlayerInventorySnapshot;
import org.core.world.position.impl.sync.SyncExactPosition;
import org.ships.implementation.bukkit.VaultService;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.human.player.live.BLivePlayer;

import java.math.BigDecimal;
import java.util.UUID;

public class BPlayerSnapshot extends BEntitySnapshot<LivePlayer> implements PlayerSnapshot {

    protected String name;
    protected PlayerInventorySnapshot inventorySnapshot;
    protected int foodLevel;
    protected double exhaustionLevel;
    protected double saturationLevel;
    protected boolean sneaking;

    public BPlayerSnapshot(LivePlayer player){
        super(player);
        setRoll(player.getRoll());
        setExhaustionLevel(player.getExhaustionLevel());
        setFood(player.getFoodLevel());
        setSaturationLevel(player.getSaturationLevel());
        setPitch(player.getPitch());
        setYaw(player.getYaw());
        this.inventorySnapshot = player.getInventory().createSnapshot();
    }

    public BPlayerSnapshot(PlayerSnapshot player){
        super(player);
        setRoll(player.getRoll());
        setExhaustionLevel(player.getExhaustionLevel());
        setFood(player.getFoodLevel());
        setSaturationLevel(player.getSaturationLevel());
        setPitch(player.getPitch());
        setYaw(player.getYaw());
        this.inventorySnapshot = player.getInventory().createSnapshot();
    }

    public BPlayerSnapshot(String name, SyncExactPosition position) {
        super(position);
        this.name = name;
        this.inventorySnapshot = null; //TODO
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getUniqueId() {
        return this.createdFrom.getUniqueId();
    }

    @Override
    public LivePlayer spawnEntity() {
        return teleportEntity(false);
    }

    @Override
    public LivePlayer teleportEntity(boolean keepInventory) {
        applyDefaults(this.createdFrom);
        this.createdFrom.setSneaking(this.sneaking);
        this.createdFrom.setExhaustionLevel(this.exhaustionLevel);
        this.createdFrom.setFood(this.foodLevel);
        this.createdFrom.setSaturationLevel(this.saturationLevel);
        if(!keepInventory) {
            this.inventorySnapshot.apply(this.createdFrom);
        }
        return this.createdFrom;
    }

    @Override
    public boolean isViewingInventory() {
        return false;
    }

    @Override
    public PlayerInventory getInventory() {
        return this.inventorySnapshot;
    }

    @Override
    public int getFoodLevel() {
        return this.foodLevel;
    }

    @Override
    public double getExhaustionLevel() {
        return this.exhaustionLevel;
    }

    @Override
    public double getSaturationLevel() {
        return this.saturationLevel;
    }

    @Override
    public boolean isSneaking() {
        return this.sneaking;
    }

    @Override
    public PlayerSnapshot setFood(int value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        this.foodLevel = value;
        return this;
    }

    @Override
    public PlayerSnapshot setExhaustionLevel(double value) throws IndexOutOfBoundsException {
        this.exhaustionLevel = value;
        return this;
    }

    @Override
    public PlayerSnapshot setSaturationLevel(double value) throws IndexOutOfBoundsException {
        this.saturationLevel = value;
        return this;
    }

    @Override
    public PlayerSnapshot setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
        return this;
    }

    @Override
    public PlayerSnapshot createSnapshot() {
        return new BPlayerSnapshot(this);
    }

    @Override
    public BigDecimal getBalance() {
        return BigDecimal.valueOf(VaultService.getBalance(((BLivePlayer)this.createdFrom).getBukkitEntity()).orElse(0.0));
    }

    @Override
    public void setBalance(BigDecimal decimal) {
        VaultService.setBalance(((BLivePlayer)this.createdFrom).getBukkitEntity(), decimal);
    }
}
