package org.ships.implementation.bukkit.event.events.connection;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.connection.ClientConnectionEvent;
import org.core.text.Text;

public class AbstractLeaveEvent implements ClientConnectionEvent.Leave {

    protected LivePlayer player;
    protected Text leaveMessage;

    @Deprecated
    public AbstractLeaveEvent(LivePlayer player, Text leaveMessage){
        this.leaveMessage = leaveMessage;
        this.player = player;
    }

    @Override
    public LivePlayer getEntity() {
        return this.player;
    }

    @Override
    @Deprecated
    public Text getLeaveMessage() {
        return this.leaveMessage;
    }

    @Override
    @Deprecated
    public Leave setLeaveMessage(Text message) {
        this.leaveMessage = message;
        return this;
    }
}
