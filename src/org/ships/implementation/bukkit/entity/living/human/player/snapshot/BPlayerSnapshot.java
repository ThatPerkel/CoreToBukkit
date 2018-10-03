package org.ships.implementation.bukkit.entity.living.human.player.snapshot;

import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.human.AbstractHuman;
import org.core.entity.living.human.player.Player;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.inventory.inventories.PlayerInventory;
import org.core.inventory.inventories.snapshots.PlayerInventorySnapshot;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.human.player.live.BLivePlayer;

public class BPlayerSnapshot extends BEntitySnapshot<Player> implements PlayerSnapshot {

    protected BLivePlayer player;
    protected String name;
    protected PlayerInventorySnapshot inventorySnapshot;
    protected int foodLevel;
    protected double exhaustionLevel;
    protected double saturationLevel;

    public BPlayerSnapshot(Player player){
        super(player.getPosition());
        setRoll(player.getRoll());
        setExhaustionLevel(player.getExhaustionLevel());
        setFood(player.getFoodLevel());
        setSaturationLevel(player.getSaturationLevel());
        setPitch(player.getPitch());
        setYaw(player.getYaw());
        this.inventorySnapshot = getInventory().createSnapshot();
        if(player instanceof BLivePlayer){
            this.player = (BLivePlayer) player;
        }else{
            this.player = ((BPlayerSnapshot)player).player;
        }
    }

    public BPlayerSnapshot(String name, ExactPosition position) {
        super(position);
        this.name = name;
        this.inventorySnapshot = null; //TODO
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Player getOrCreateEntity() {
        this.player.setExhaustionLevel(this.exhaustionLevel);
        this.player.setFood(this.foodLevel);
        this.player.setSaturationLevel(this.saturationLevel);
        this.player.setPitch(this.pitch);
        this.player.setRoll(this.roll);
        this.player.setYaw(this.yaw);
        this.player.setPosition(this.position);
        this.inventorySnapshot.apply(this.player);
        return this.player;
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
    public AbstractHuman setFood(int value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        this.foodLevel = value;
        return this;
    }

    @Override
    public AbstractHuman setExhaustionLevel(double value) throws IndexOutOfBoundsException {
        this.exhaustionLevel = value;
        return this;
    }

    @Override
    public AbstractHuman setSaturationLevel(double value) throws IndexOutOfBoundsException {
        this.saturationLevel = value;
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
}
