package org.ships.implementation.bukkit.inventory.inventories.live.entity;

import org.core.inventory.item.ItemStack;
import org.core.inventory.parts.Slot;
import org.ships.implementation.bukkit.inventory.item.BItemStack;

import java.util.Optional;

public class BLiveDroppedItemSlot implements Slot {

    protected org.bukkit.entity.Item item;

    public BLiveDroppedItemSlot(org.bukkit.entity.Item item){
        this.item = item;
    }

    @Override
    public Optional<Integer> getPosition() {
        return Optional.empty();
    }

    @Override
    public Optional<ItemStack> getItem() {
        org.bukkit.inventory.ItemStack stack = this.item.getItemStack();
        if(stack == null){
            return Optional.empty();
        }
        return Optional.of(new BItemStack(stack));
    }

    @Override
    public Slot setItem(ItemStack stack) {
        if(stack == null){
            return this;
        }
        org.bukkit.inventory.ItemStack stack2 = ((BItemStack)stack).getBukkitItem();
        this.item.setItemStack(stack2);
        return this;
    }
}
