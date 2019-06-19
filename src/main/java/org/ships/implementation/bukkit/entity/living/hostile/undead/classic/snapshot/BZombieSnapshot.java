package org.ships.implementation.bukkit.entity.living.hostile.undead.classic.snapshot;

import org.bukkit.entity.Zombie;
import org.core.entity.EntitySnapshot;
import org.core.entity.EntityType;
import org.core.entity.EntityTypes;
import org.core.entity.living.AgeableEntity;
import org.core.entity.living.hostile.undead.classic.ClassicZombie;
import org.core.entity.living.hostile.undead.classic.ClassicZombieSnapshot;
import org.core.inventory.inventories.general.entity.ZombieInventory;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.entity.BEntitySnapshot;
import org.ships.implementation.bukkit.world.position.BExactPosition;

public class BZombieSnapshot extends BEntitySnapshot<ClassicZombie> implements ClassicZombieSnapshot {

    protected boolean isAdult;

    public BZombieSnapshot(ExactPosition position) {
        super(position);
    }

    public BZombieSnapshot(ClassicZombie zombie){
        super(zombie);
    }

    @Override
    public ClassicZombie getOrCreateEntity() {
        org.bukkit.Location loc = ((BExactPosition)this.position).getBukkitLocation();
        loc.setPitch((float)this.pitch);
        loc.setYaw((float)this.yaw);
        org.bukkit.entity.Zombie zombie = (Zombie)loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.ZOMBIE);
        zombie.setBaby(!this.isAdult);
        return this;
    }

    @Override
    public ZombieInventory getInventory() {
        return null;
    }

    @Override
    public boolean isAdult() {
        return this.isAdult;
    }

    @Override
    public AgeableEntity setAdult(boolean check) {
        this.isAdult = check;
        return this;
    }

    @Override
    public EntityType<ClassicZombie, ClassicZombieSnapshot> getType() {
        return EntityTypes.ZOMBIE;
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return new BZombieSnapshot(this);
    }
}
