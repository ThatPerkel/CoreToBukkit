package org.ships.implementation.bukkit.world.position.block.details.blocks.data.keyed;

import org.core.world.position.block.details.data.keyed.OpenableKeyedData;

import java.util.Optional;

public class BOpenableKeyedData implements OpenableKeyedData {

    private org.bukkit.block.data.Openable data;

    public BOpenableKeyedData(org.bukkit.block.data.Openable data){
        this.data = data;
    }

    @Override
    public Optional<Boolean> getData() {
        return Optional.of(this.data.isOpen());
    }

    @Override
    public void setData(Boolean value) {
        this.data.setOpen(value);
    }
}
