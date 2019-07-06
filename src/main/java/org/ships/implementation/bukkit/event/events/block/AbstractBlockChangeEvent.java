package org.ships.implementation.bukkit.event.events.block;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.block.BlockChangeEvent;
import org.core.world.expload.Explosion;
import org.core.world.position.BlockPosition;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.BlockDetails;

public class AbstractBlockChangeEvent implements BlockChangeEvent {

    protected BlockPosition position;
    protected BlockDetails before;
    protected BlockDetails after;

    public AbstractBlockChangeEvent(BlockPosition pos, BlockDetails before, BlockDetails after){
        this.position = pos;
        this.before = before;
        this.after = after;
    }

    @Override
    public BlockDetails getBeforeState() {
        return this.before;
    }

    @Override
    public BlockDetails getAfterState() {
        return this.after;
    }

    @Override
    public BlockPosition getPosition() {
        return this.position;
    }

    public static class BreakBlockChangeExplode extends AbstractBlockChangeEvent implements BlockChangeEvent.Break.ByExplosion {

        protected boolean cancelled;
        protected Explosion explosion;

        public BreakBlockChangeExplode(BlockPosition pos, Explosion explosion) {
            super(pos, pos.getBlockDetails(), BlockTypes.AIR.get().getDefaultBlockDetails());
            this.explosion = explosion;
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean value) {
            this.cancelled = value;
        }

        @Override
        public Explosion getExplosion() {
            return this.explosion;
        }
    }

    public static class BreakBlockChangeEventPlayer extends AbstractBlockChangeEvent implements BlockChangeEvent.Break.ByPlayer{

        protected LivePlayer player;
        protected boolean isCancelled;

        public BreakBlockChangeEventPlayer(BlockPosition pos, LivePlayer player) {
            super(pos, pos.getBlockDetails(), BlockTypes.AIR.get().getDefaultBlockDetails());
            this.player = player;
        }

        @Override
        public LivePlayer getEntity() {
            return this.player;
        }

        @Override
        public boolean isCancelled() {
            return this.isCancelled;
        }

        @Override
        public void setCancelled(boolean value) {
            this.isCancelled = value;
        }
    }
}
