package org.ships.implementation.bukkit.inventory.inventories.live.entity;

import org.bukkit.Material;
import org.core.entity.living.human.player.LivePlayer;
import org.core.inventory.inventories.live.entity.LivePlayerInventory;
import org.core.inventory.inventories.snapshots.entity.PlayerInventorySnapshot;
import org.core.inventory.item.ItemStack;
import org.core.inventory.parts.*;
import org.ships.implementation.bukkit.entity.living.human.player.live.BLivePlayer;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.entity.BPlayerInventorySnapshot;
import org.ships.implementation.bukkit.inventory.item.BItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BLivePlayerInventory implements LivePlayerInventory {

    private class PlayerArmorSlots implements ArmorPart {

        BLivePlayerInventory.PlayerItemSlot helmentSlot = new BLivePlayerInventory.PlayerItemSlot(-1);
        BLivePlayerInventory.PlayerItemSlot armorSlot = new BLivePlayerInventory.PlayerItemSlot(-1);
        BLivePlayerInventory.PlayerItemSlot leggingsSlot = new BLivePlayerInventory.PlayerItemSlot(-1);
        BLivePlayerInventory.PlayerItemSlot shoeSlot = new BLivePlayerInventory.PlayerItemSlot(-1);

        @Override
        public Slot getHelmetSlot() {
            return this.helmentSlot;
        }

        @Override
        public Slot getArmorSlot() {
            return this.armorSlot;
        }

        @Override
        public Slot getLeggingsSlot() {
            return this.leggingsSlot;
        }

        @Override
        public Slot getShoesSlot() {
            return this.shoeSlot;
        }
    }

    private class PlayerHotbar implements Hotbar {

        Set<Slot> slots = new HashSet<>();

        public PlayerHotbar(){
            for(int A = 0; A < 9; A++){
                this.slots.add(new BLivePlayerInventory.PlayerItemSlot(A));
            }
        }

        @Override
        public int getSelectedSlotPos() {
            return BLivePlayerInventory.this.player.getBukkitEntity().getInventory().getHeldItemSlot();
        }

        @Override
        public Set<Slot> getSlots() {
            return this.slots;
        }
    }

    private class PlayerItemSlot implements Slot {

        int position;

        public PlayerItemSlot(int slot){
            this.position = slot;
        }

        @Override
        public Optional<Integer> getPosition() {
            return Optional.of(this.position);
        }

        @Override
        public Optional<ItemStack> getItem() {
            org.bukkit.inventory.ItemStack stack = BLivePlayerInventory.this.player.getBukkitEntity().getInventory().getItem(this.position);
            if(stack == null || stack.getType().equals(Material.VOID_AIR)){
                return Optional.empty();
            }
            return Optional.of(new BItemStack(stack));
        }

        @Override
        public Slot setItem(ItemStack stack) {
            org.bukkit.inventory.ItemStack bstack = ((BItemStack)stack).getBukkitItem();
            BLivePlayerInventory.this.player.getBukkitEntity().getInventory().setItem(this.position, bstack);
            return this;
        }

    }

    BLivePlayer player;
    BLivePlayerInventory.PlayerItemSlot offHandSlot = new BLivePlayerInventory.PlayerItemSlot(-1);
    BLivePlayerInventory.PlayerArmorSlots armorSlots = new BLivePlayerInventory.PlayerArmorSlots();
    BLivePlayerInventory.PlayerHotbar hotbar = new BLivePlayerInventory.PlayerHotbar();

    public BLivePlayerInventory(BLivePlayer player){
        this.player = player;
    }

    @Override
    public ArmorPart getArmor() {
        return this.armorSlots;
    }

    @Override
    public Slot getOffHoldingItem() {
        return this.offHandSlot;
    }

    @Override
    public Hotbar getHotbar() {
        return this.hotbar;
    }

    @Override
    public Grid2x2 getCraftingGrid() {
        return null;
    }

    @Override
    public MainPlayerInventory getMainInventory() {
        return null;
    }

    @Override
    public PlayerInventorySnapshot createSnapshot() {
        return new BPlayerInventorySnapshot(this);
    }

    @Override
    public LivePlayer getAttachedEntity() {
        return this.player;
    }

}
