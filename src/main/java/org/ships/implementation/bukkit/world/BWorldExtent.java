package org.ships.implementation.bukkit.world;

import org.core.CorePlugin;
import org.core.entity.Entity;
import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.platform.BukkitPlatform;
import org.ships.implementation.bukkit.world.position.BBlockPosition;
import org.ships.implementation.bukkit.world.position.BExactPosition;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BWorldExtent implements WorldExtent {

    protected org.bukkit.World world;

    public BWorldExtent(org.bukkit.World world){
        this.world = world;
    }

    public org.bukkit.World getBukkitWorld(){
        return this.world;
    }

    @Override
    public ExactPosition getPosition(double x, double y, double z) {
        return new BExactPosition(x, y, z, this.world);
    }

    @Override
    public BlockPosition getPosition(int x, int y, int z) {
        return new BBlockPosition(x, y, z, this.world);
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public Set<Entity> getEntities() {
        Set<Entity> entities = new HashSet<>();
        this.world.getEntities().forEach(e -> {
            Entity entity = ((BukkitPlatform)CorePlugin.getPlatform()).createEntityInstance(e);
            if(entity == null){
                return;
            }
            entities.add(entity);
        });
        return entities;
    }

    @Override
    public String getName() {
        return this.world.getName();
    }

    @Override
    public UUID getUniquieId() {
        return this.world.getUID();
    }

    @Override
    public String getPlatformUniquieId() {
        return getName();
    }
}
