package org.ships.implementation.bukkit.world;

import org.core.world.WorldExtent;
import org.core.world.position.BlockPosition;
import org.core.world.position.ExactPosition;
import org.ships.implementation.bukkit.world.position.BBlockPosition;
import org.ships.implementation.bukkit.world.position.BExactPosition;

public class BWorldExtent implements WorldExtent {

    org.bukkit.World world;

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
}
