package org.ships.implementation.bukkit.entity.living.human.player.live;

import org.bukkit.Bukkit;
import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.inventory.inventories.general.entity.PlayerInventory;
import org.core.source.viewer.CommandViewer;
import org.core.text.Text;
import org.ships.implementation.bukkit.VaultService;
import org.ships.implementation.bukkit.entity.BLiveEntity;
import org.ships.implementation.bukkit.entity.living.human.player.snapshot.BPlayerSnapshot;
import org.ships.implementation.bukkit.inventory.inventories.live.entity.BLivePlayerInventory;
import org.ships.implementation.bukkit.text.BText;

import java.math.BigDecimal;
import java.util.UUID;

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
        return player2.getBukkitEntity().equals(this.getBukkitEntity());
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
    public String getName() {
        return getBukkitEntity().getName();
    }

    @Override
    public UUID getUniqueId() {
        return getBukkitEntity().getUniqueId();
    }

    @Override
    public boolean isSneaking() {
        return getBukkitEntity().isSneaking();
    }

    @Override
    public LivePlayer setFood(int value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setFoodLevel(value);
        return this;
    }

    @Override
    public LivePlayer setExhaustionLevel(double value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setExhaustion((float)value);
        return this;
    }

    @Override
    public LivePlayer setSaturationLevel(double value) throws IndexOutOfBoundsException {
        if(value > 20){
            throw new IndexOutOfBoundsException();
        }
        getBukkitEntity().setSaturation((float)value);
        return this;
    }

    @Override
    public LivePlayer setSneaking(boolean sneaking) {
        getBukkitEntity().setSneaking(sneaking);
        return this;
    }

    @Override
    public boolean hasPermission(String permission) {
        org.bukkit.entity.Player player = getBukkitEntity();
        String[] blocks = permission.split(".");
        for(int A = 0; A < blocks.length; A++){
            StringBuilder builder = new StringBuilder();
            for (String block : blocks) {
                builder.append(block);
            }
            if(player.hasPermission(builder.toString() + ".*")){
                return true;
            }
        }
        return player.hasPermission(permission);
    }

    @Override
    public PlayerSnapshot createSnapshot() {
        return new BPlayerSnapshot(this);
    }

    @Override
    public BLivePlayer setGravity(boolean check) {
        getBukkitEntity().setGravity(check);
        return this;
    }

    @Override
    public boolean hasGravity() {
        return getBukkitEntity().hasGravity();
    }

    @Override
    public CommandViewer sendMessage(Text message) {
        getBukkitEntity().sendMessage(((BText)message).toBukkitString());
        return this;
    }

    @Override
    public CommandViewer sendMessagePlain(String message) {
        getBukkitEntity().sendMessage(org.bukkit.ChatColor.stripColor(message));
        return this;
    }

    @Override
    public boolean sudo(String wholeCommand) {
        return Bukkit.dispatchCommand(getBukkitEntity(), wholeCommand);
    }

    @Override
    public BigDecimal getBalance() {
        return BigDecimal.valueOf(VaultService.getBalance(this.getBukkitEntity()).orElse(0.0));
    }

    @Override
    public void setBalance(BigDecimal decimal) {
        VaultService.setBalance(this.getBukkitEntity(), decimal);
    }
}
