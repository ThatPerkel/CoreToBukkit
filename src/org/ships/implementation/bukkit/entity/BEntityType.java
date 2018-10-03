package org.ships.implementation.bukkit.entity;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.living.hostile.undead.classic.ClassicZombie;
import org.core.entity.living.hostile.undead.classic.ClassicZombieSnapshot;

public interface BEntityType <E extends Entity, S extends EntitySnapshot<E>> extends EntityType<E, S> {

    public org.bukkit.entity.EntityType getBukkitEntityType();

    class ZombieType implements BEntityType<ClassicZombie, ClassicZombieSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.ZOMBIE;
        }

        @Override
        public Class<ClassicZombie> getEntityClass() {
            return ClassicZombie.class;
        }

        @Override
        public Class<ClassicZombieSnapshot> getSnapshotClass() {
            return ClassicZombieSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Zombie";
        }
    }
}
