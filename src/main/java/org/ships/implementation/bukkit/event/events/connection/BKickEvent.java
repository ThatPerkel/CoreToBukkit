package org.ships.implementation.bukkit.event.events.connection;

import org.core.entity.living.human.player.LivePlayer;
import org.core.event.events.connection.ClientConnectionEvent;
import org.core.text.Text;

public class BKickEvent extends AbstractLeaveEvent implements ClientConnectionEvent.Leave.Kick {

    public BKickEvent(LivePlayer player, Text leaveMessage) {
        super(player, leaveMessage);
    }
}
