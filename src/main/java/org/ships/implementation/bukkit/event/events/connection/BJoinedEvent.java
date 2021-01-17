package org.ships.implementation.bukkit.event.events.connection;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.connection.ClientConnectionEvent;

public class BJoinedEvent implements ClientConnectionEvent.Incoming.Joined {

    private LivePlayer player;

    public BJoinedEvent(LivePlayer player){
        this.player = player;
    }

    @Override
    public LivePlayer getEntity() {
        return this.player;
    }
}
