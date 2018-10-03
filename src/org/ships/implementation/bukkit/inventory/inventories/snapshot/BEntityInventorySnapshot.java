package org.ships.implementation.bukkit.inventory.inventories.snapshot;

import org.bukkit.entity.LivingEntity;
import org.core.entity.Entity;
import org.core.inventory.inventories.snapshots.EntityInventorySnapshot;
import org.ships.implementation.bukkit.entity.BLiveEntity;
import org.ships.implementation.bukkit.inventory.item.BItemStack;

public interface BEntityInventorySnapshot <E extends Entity> extends EntityInventorySnapshot<E> {

    @Override
    default void apply(E entity) {
        BLiveEntity<? extends LivingEntity> entity1 = (BLiveEntity)entity;
        org.bukkit.inventory.EntityEquipment equipment = entity1.getBukkitEntity().getEquipment();
        getMainHoldingItem().getItem().ifPresent(i -> equipment.setItemInMainHand(((BItemStack)i).getBukkitItem()));
        getOffHoldingItem().getItem().ifPresent(i -> equipment.setItemInOffHand(((BItemStack)i).getBukkitItem()));
        getArmor().getShoesSlot().getItem().ifPresent(i -> equipment.setBoots(((BItemStack)i).getBukkitItem()));
        getArmor().getLeggingsSlot().getItem().ifPresent(i -> equipment.setLeggings(((BItemStack)i).getBukkitItem()));
        getArmor().getArmorSlot().getItem().ifPresent(i -> equipment.setChestplate(((BItemStack)i).getBukkitItem()));
        getArmor().getHelmetSlot().getItem().ifPresent(i -> equipment.setHelmet(((BItemStack)i).getBukkitItem()));
    }
}
