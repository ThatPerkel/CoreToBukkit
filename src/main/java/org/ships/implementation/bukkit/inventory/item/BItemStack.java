package org.ships.implementation.bukkit.inventory.item;

import org.core.inventory.item.ItemStack;
import org.core.inventory.item.ItemType;

public class BItemStack implements ItemStack {

    protected org.bukkit.inventory.ItemStack stack;

    public BItemStack(org.bukkit.inventory.ItemStack stack){
        this.stack = stack;
    }

    public org.bukkit.inventory.ItemStack getBukkitItem(){
        return this.stack;
    }

    @Override
    public ItemType getType() {
        return new BItemType(this.stack.getType());
    }

    @Override
    public int getQuantity() {
        return this.stack.getAmount();
    }

    @Override
    public ItemStack copy() {
        return new BItemStack(this.stack.clone());
    }

    @Override
    public String toString(){
        if(this.stack == null){
            return super.toString();
        }else{
            return super.toString() + "{ID: " + getType().getId() + " Quantity: " + this.stack.getAmount() + "}";
        }
    }
}