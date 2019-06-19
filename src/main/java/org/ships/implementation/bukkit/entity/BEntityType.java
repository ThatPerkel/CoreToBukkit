package org.ships.implementation.bukkit.entity;

import org.core.entity.Entity;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.living.animal.chicken.Chicken;
import org.core.entity.living.animal.chicken.ChickenSnapshot;
import org.core.entity.living.animal.cow.Cow;
import org.core.entity.living.animal.cow.CowSnapshot;
import org.core.entity.living.bat.Bat;
import org.core.entity.living.bat.BatSnapshot;
import org.core.entity.living.fish.cod.Cod;
import org.core.entity.living.fish.cod.CodSnapshot;
import org.core.entity.living.hostile.undead.classic.ClassicZombie;
import org.core.entity.living.hostile.undead.classic.ClassicZombieSnapshot;
import org.core.entity.projectile.item.snowball.SnowballEntity;
import org.core.entity.projectile.item.snowball.SnowballEntitySnapshot;
import org.core.entity.scene.droppeditem.DroppedItem;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.entity.scene.itemframe.ItemFrame;
import org.core.entity.scene.itemframe.ItemFrameSnapshot;

public interface BEntityType <E extends Entity, S extends EntitySnapshot<E>> extends EntityType<E, S> {

    org.bukkit.entity.EntityType getBukkitEntityType();

    class BatType implements BEntityType<Bat, BatSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.BAT;
        }

        @Override
        public Class<Bat> getEntityClass() {
            return Bat.class;
        }

        @Override
        public Class<BatSnapshot> getSnapshotClass() {
            return BatSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Bat";
        }
    }

    class SnowballType implements BEntityType<SnowballEntity, SnowballEntitySnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.SNOWBALL;
        }

        @Override
        public Class<SnowballEntity> getEntityClass() {
            return SnowballEntity.class;
        }

        @Override
        public Class<SnowballEntitySnapshot> getSnapshotClass() {
            return SnowballEntitySnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Snowball";
        }
    }

    class DroppedItemType implements BEntityType<DroppedItem, DroppedItemSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.DROPPED_ITEM;
        }

        @Override
        public Class<DroppedItem> getEntityClass() {
            return DroppedItem.class;
        }

        @Override
        public Class<DroppedItemSnapshot> getSnapshotClass() {
            return DroppedItemSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "DroppedItem";
        }
    }

    class CodType implements BEntityType<Cod, CodSnapshot> {

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.COD;
        }

        @Override
        public Class<Cod> getEntityClass() {
            return Cod.class;
        }

        @Override
        public Class<CodSnapshot> getSnapshotClass() {
            return CodSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Cod";
        }
    }

    class ItemFrameType implements BEntityType<ItemFrame, ItemFrameSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.ITEM_FRAME;
        }

        @Override
        public Class<ItemFrame> getEntityClass() {
            return ItemFrame.class;
        }

        @Override
        public Class<ItemFrameSnapshot> getSnapshotClass() {
            return ItemFrameSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "ItemFrame";
        }
    }

    class CowType implements BEntityType<Cow, CowSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.COW;
        }

        @Override
        public Class<Cow> getEntityClass() {
            return Cow.class;
        }

        @Override
        public Class<CowSnapshot> getSnapshotClass() {
            return CowSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Cow";
        }
    }

    class ChickenType implements BEntityType<Chicken, ChickenSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.CHICKEN;
        }

        @Override
        public Class<Chicken> getEntityClass() {
            return Chicken.class;
        }

        @Override
        public Class<ChickenSnapshot> getSnapshotClass() {
            return ChickenSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Chicken";
        }
    }

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
