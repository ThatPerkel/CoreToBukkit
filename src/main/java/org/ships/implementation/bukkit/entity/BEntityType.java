package org.ships.implementation.bukkit.entity;

import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.LiveEntity;
import org.core.entity.living.animal.chicken.ChickenSnapshot;
import org.core.entity.living.animal.chicken.LiveChicken;
import org.core.entity.living.animal.cow.CowSnapshot;
import org.core.entity.living.animal.cow.LiveCow;
import org.core.entity.living.bat.BatSnapshot;
import org.core.entity.living.bat.LiveBat;
import org.core.entity.living.fish.cod.CodSnapshot;
import org.core.entity.living.fish.cod.LiveCod;
import org.core.entity.living.hostile.undead.classic.ClassicZombieSnapshot;
import org.core.entity.living.hostile.undead.classic.LiveClassicZombie;
import org.core.entity.living.human.HumanSnapshot;
import org.core.entity.living.human.LiveHuman;
import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.living.human.player.PlayerSnapshot;
import org.core.entity.projectile.item.snowball.LiveSnowballEntity;
import org.core.entity.projectile.item.snowball.SnowballEntitySnapshot;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.entity.scene.droppeditem.LiveDroppedItem;
import org.core.entity.scene.itemframe.ItemFrameSnapshot;
import org.core.entity.scene.itemframe.LiveItemFrame;

public interface BEntityType <E extends LiveEntity, S extends EntitySnapshot<E>> extends EntityType<E, S> {

    org.bukkit.entity.EntityType getBukkitEntityType();

    class BatType implements BEntityType<LiveBat, BatSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.BAT;
        }

        @Override
        public Class<LiveBat> getEntityClass() {
            return LiveBat.class;
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

    class SnowballType implements BEntityType<LiveSnowballEntity, SnowballEntitySnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.SNOWBALL;
        }

        @Override
        public Class<LiveSnowballEntity> getEntityClass() {
            return LiveSnowballEntity.class;
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

    class DroppedItemType implements BEntityType<LiveDroppedItem, DroppedItemSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.DROPPED_ITEM;
        }

        @Override
        public Class<LiveDroppedItem> getEntityClass() {
            return LiveDroppedItem.class;
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

    class PlayerType implements BEntityType<LivePlayer, PlayerSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.PLAYER;
        }

        @Override
        public Class<LivePlayer> getEntityClass() {
            return LivePlayer.class;
        }

        @Override
        public Class<PlayerSnapshot> getSnapshotClass() {
            return PlayerSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Player";
        }
    }

    class HumanType implements BEntityType<LiveHuman, HumanSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.PLAYER;
        }

        @Override
        public Class<LiveHuman> getEntityClass() {
            return LiveHuman.class;
        }

        @Override
        public Class<HumanSnapshot> getSnapshotClass() {
            return HumanSnapshot.class;
        }

        @Override
        public String getId() {
            return "minecraft:" + getName().toLowerCase();
        }

        @Override
        public String getName() {
            return "Human";
        }
    }

    class CodType implements BEntityType<LiveCod, CodSnapshot> {

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.COD;
        }

        @Override
        public Class<LiveCod> getEntityClass() {
            return LiveCod.class;
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

    class ItemFrameType implements BEntityType<LiveItemFrame, ItemFrameSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.ITEM_FRAME;
        }

        @Override
        public Class<LiveItemFrame> getEntityClass() {
            return LiveItemFrame.class;
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

    class CowType implements BEntityType<LiveCow, CowSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.COW;
        }

        @Override
        public Class<LiveCow> getEntityClass() {
            return LiveCow.class;
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

    class ChickenType implements BEntityType<LiveChicken, ChickenSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.CHICKEN;
        }

        @Override
        public Class<LiveChicken> getEntityClass() {
            return LiveChicken.class;
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

    class ZombieType implements BEntityType<LiveClassicZombie, ClassicZombieSnapshot>{

        @Override
        public org.bukkit.entity.EntityType getBukkitEntityType() {
            return org.bukkit.entity.EntityType.ZOMBIE;
        }

        @Override
        public Class<LiveClassicZombie> getEntityClass() {
            return LiveClassicZombie.class;
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
