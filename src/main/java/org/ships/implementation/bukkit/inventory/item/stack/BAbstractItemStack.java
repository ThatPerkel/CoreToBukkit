package org.ships.implementation.bukkit.inventory.item.stack;

import org.core.inventory.item.stack.ItemStack;
import org.core.inventory.item.ItemType;
import org.core.inventory.item.stack.ItemStackSnapshot;
import org.core.text.Text;
import org.ships.implementation.bukkit.inventory.item.BItemType;
import org.ships.implementation.bukkit.text.BText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BAbstractItemStack implements ItemStack {

    protected org.bukkit.inventory.ItemStack stack;

    public BAbstractItemStack(org.bukkit.inventory.ItemStack stack){
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
    public List<Text> getLore() {
        List<Text> lore = new ArrayList<>();
        this.stack.getItemMeta().getLore().stream().forEach(l -> {
            lore.add(new BText(l));
        });
        return lore;
    }

    @Override
    public ItemStack setLore(Collection<Text> lore) {
        List<String> newLore = new ArrayList<>();
        lore.stream().forEach(l -> newLore.add(((BText)l).toBukkitString()));
        this.stack.getItemMeta().setLore(newLore);
        return this;
    }

    @Override
    public ItemStackSnapshot createSnapshot() {
        return new BItemStackSnapshot(this);
    }
}
