package org.ships.implementation.bukkit.event.events.block;

import org.core.entity.living.human.player.LivePlayer;
import org.core.entity.scene.droppeditem.DroppedItemSnapshot;
import org.core.event.events.block.BlockChangeEvent;
import org.core.world.expload.Explosion;
import org.core.world.position.block.BlockTypes;
import org.core.world.position.block.details.BlockDetails;
import org.core.world.position.block.details.BlockSnapshot;
import org.core.world.position.impl.sync.SyncBlockPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AbstractBlockChangeEvent implements BlockChangeEvent {

    protected SyncBlockPosition position;
    protected BlockDetails before;
    protected BlockDetails after;

    public AbstractBlockChangeEvent(SyncBlockPosition pos, BlockDetails before, BlockDetails after){
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
    public SyncBlockPosition getPosition() {
        return this.position;
    }

    public static class PlaceBlockPlayerPostEvent extends AbstractBlockChangeEvent implements BlockChangeEvent.Place.Post.ByPlayer {

        protected LivePlayer player;
        protected boolean cancelled;
        protected Collection<BlockSnapshot<SyncBlockPosition>> collection;

        public PlaceBlockPlayerPostEvent(SyncBlockPosition pos, BlockDetails before, BlockDetails after, LivePlayer player, Collection<BlockSnapshot<SyncBlockPosition>> affected) {
            super(pos, before, after);
            this.player = player;
            this.collection = affected;

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
        public LivePlayer getEntity() {
            return this.player;
        }

        @Override
        public Collection<BlockSnapshot<SyncBlockPosition>> getAffected() {
            return this.collection;
        }
    }

    public static class BreakBlockPostEvent extends AbstractBlockChangeEvent implements BlockChangeEvent.Break.Post.ByPlayer {

        Collection<DroppedItemSnapshot> items;
        List<DroppedItemSnapshot> toRemove = new ArrayList<>();
        LivePlayer player;

        public BreakBlockPostEvent(BlockDetails pre, SyncBlockPosition pos, LivePlayer player, Collection<DroppedItemSnapshot> items) {
            super(pos, pre, pos.getBlockDetails());
            this.items = items;
            this.player = player;
        }

        @Override
        public Collection<DroppedItemSnapshot> getItems() {
            return this.items;
        }

        @Override
        public Post removeItem(DroppedItemSnapshot snapshot) {
            this.toRemove.add(snapshot);
            return this;
        }

        @Override
        public LivePlayer getEntity() {
            return this.player;
        }
    }

    public static class BreakBlockChangeExplode extends AbstractBlockChangeEvent implements BlockChangeEvent.Break.Pre.ByExplosion {

        protected boolean cancelled;
        protected Explosion explosion;

        public BreakBlockChangeExplode(SyncBlockPosition pos, Explosion explosion) {
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

    public static class BreakBlockChangeEventPlayer extends AbstractBlockChangeEvent implements BlockChangeEvent.Break.Pre.ByPlayer{

        protected LivePlayer player;
        protected boolean isCancelled;

        public BreakBlockChangeEventPlayer(SyncBlockPosition pos, LivePlayer player) {
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
