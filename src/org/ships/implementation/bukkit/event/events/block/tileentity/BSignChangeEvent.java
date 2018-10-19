package org.ships.implementation.bukkit.event.events.block.tileentity;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.block.tileentity.SignChangeEvent;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.entity.sign.SignTileEntitySnapshot;
import org.ships.implementation.bukkit.world.position.block.entity.sign.BSignEntitySnapshot;

public class BSignChangeEvent implements SignChangeEvent.ByPlayer {

    protected boolean isCancelled;
    protected SignTileEntitySnapshot original;
    protected SignTileEntitySnapshot to;
    protected BlockPosition position;
    protected LivePlayer player;

    public BSignChangeEvent(LivePlayer player, BlockPosition position, String... lines){
        this.position = position;
        this.original = new BSignEntitySnapshot(lines);
        this.player = player;
        this.to = new BSignEntitySnapshot(lines);
    }

    @Override
    public SignTileEntitySnapshot getTo() {
        return this.to;
    }

    @Override
    public SignChangeEvent setTo(SignTileEntitySnapshot snapshot) {
        this.to = snapshot;
        return this;
    }

    @Override
    public SignTileEntitySnapshot getFrom() {
        return this.original;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean value) {
        this.isCancelled = value;
    }

    @Override
    public BlockPosition getPosition() {
        return this.position;
    }

    @Override
    public LivePlayer getEntity() {
        return this.player;
    }
}
