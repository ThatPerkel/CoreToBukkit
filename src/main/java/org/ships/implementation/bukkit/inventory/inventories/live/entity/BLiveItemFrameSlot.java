package org.ships.implementation.bukkit.inventory.inventories.live.entity;

import org.core.inventory.item.ItemStack;
import org.core.inventory.parts.Slot;
import org.ships.implementation.bukkit.inventory.item.BItemStack;

import java.util.Optional;

public class BLiveItemFrameSlot implements Slot {

    protected org.bukkit.entity.ItemFrame frame;

    public BLiveItemFrameSlot(org.bukkit.entity.ItemFrame frame){
        this.frame = frame;
    }

    @Override
    public Optional<Integer> getPosition() {
        return Optional.empty();
    }

    @Override
    public Optional<ItemStack> getItem() {
        org.bukkit.inventory.ItemStack stack = this.frame.getItem();
        if(stack == null){
            return Optional.empty();
        }
        return Optional.of(new BItemStack(stack));
    }

    @Override
    public Slot setItem(ItemStack stack) {
        if(stack == null){
            this.frame.setItem(null, false);
            return this;
        }
        org.bukkit.inventory.ItemStack stack2 = ((BItemStack)stack).getBukkitItem();
        this.frame.setItem(stack2, false);
        return this;
    }
}
