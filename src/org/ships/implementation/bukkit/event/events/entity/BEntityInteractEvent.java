package org.ships.implementation.bukkit.event.events.entity;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.entity.EntityInteractEvent;
import org.core.world.direction.Direction;
import org.core.world.position.BlockPosition;

public class BEntityInteractEvent {

    public static class PlayerInteractWithBlock implements EntityInteractEvent.WithBlock.AsPlayer {

        protected BlockPosition position;
        protected LivePlayer player;
        protected Direction direction;
        protected boolean cancelled;

        public PlayerInteractWithBlock(BlockPosition position, Direction clickedFace, LivePlayer player){
            this.player = player;
            this.position = position;
            this.direction = clickedFace;
        }

        @Override
        public BlockPosition getInteractPosition() {
            return this.position;
        }

        @Override
        public LivePlayer getEntity() {
            return this.player;
        }

        @Override
        public Direction getClickedBlockFace() {
            return this.direction;
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean value) {
            this.cancelled = value;
        }
    }
}
