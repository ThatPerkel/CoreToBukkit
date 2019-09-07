package org.ships.implementation.bukkit.entity.living.animal.type;

import org.core.entity.living.animal.parrot.ParrotType;

public class BParrotType implements ParrotType {

    protected org.bukkit.entity.Parrot.Variant type;

    public BParrotType(org.bukkit.entity.Parrot.Variant variant){
        this.type = variant;
    }

    @Override
    public String getId() {
        return "minecraft:parrot_variant_" + this.type.name().toLowerCase();
    }

    @Override
    public String getName() {
        return this.type.name();
    }
}
