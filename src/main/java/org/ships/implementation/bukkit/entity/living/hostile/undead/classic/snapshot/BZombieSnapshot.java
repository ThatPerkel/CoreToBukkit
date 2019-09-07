package org.ships.implementation.bukkit.entity.living.hostile.undead.classic.snapshot;

import org.bukkit.entity.Zombie;
import org.core.entity.EntitySnapshot;
import org.core.entity.LiveEntity;
import org.core.entity.living.AgeableEntity;
import org.core.entity.living.hostile.undead.classic.ClassicZombieSnapshot;
import org.core.entity.living.hostile.undead.classic.LiveClassicZombie;
import org.core.inventory.inventories.general.entity.ZombieInventory;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.entity.living.hostile.undead.classic.live.BLiveZombie;
import org.ships.implementation.bukkit.inventory.inventories.snapshot.entity.BClassicZombieInventorySnapshot;
import org.ships.implementation.bukkit.world.position.BExactPosition;

public class BZombieSnapshot extends BEntitySnapshot<LiveClassicZombie> implements ClassicZombieSnapshot {

    protected boolean isAdult;
    protected BClassicZombieInventorySnapshot inventory;

    public BZombieSnapshot(ExactPosition position) {
        super(position);
    }

    public BZombieSnapshot(LiveClassicZombie zombie){
        super(zombie);
        this.isAdult = zombie.isAdult();
        this.inventory = new BClassicZombieInventorySnapshot(zombie.getInventory());
    }

    public BZombieSnapshot(ClassicZombieSnapshot zombie){
        super(zombie);
        this.isAdult = zombie.isAdult();
        this.inventory = new BClassicZombieInventorySnapshot(zombie.getInventory());
    }


    @Override
    public LiveClassicZombie spawnEntity() {
        org.bukkit.Location loc = ((BExactPosition)this.position).getBukkitLocation();
        loc.setPitch((float)this.pitch);
        loc.setYaw((float)this.yaw);
        org.bukkit.entity.Zombie zombie = (Zombie)loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.ZOMBIE);
        zombie.setBaby(!this.isAdult);
        BLiveZombie coreZombie = new BLiveZombie(zombie);
        applyDefaults(coreZombie);
        this.inventory.apply(coreZombie);
        return coreZombie;
    }

    @Override
    public ZombieInventory getInventory() {
        return this.inventory;
    }

    @Override
    public boolean isAdult() {
        return this.isAdult;
    }

    @Override
    public AgeableEntity<EntitySnapshot<? extends LiveEntity>> setAdult(boolean check) {
        this.isAdult = check;
        return this;
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return new BZombieSnapshot(this);
    }
}
