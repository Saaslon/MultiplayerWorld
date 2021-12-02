package de.saaslon.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.saaslon.global.MoveUpdateEvent;
import de.saaslon.server.handlers.PlayerHandler;
import de.saaslon.server.supers.ServerPlayer;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        if(object instanceof MoveUpdateEvent) {
            final ServerPlayer serverPlayer = PlayerHandler.INSTANCE.getPlayerByConnection(connection);

            final MoveUpdateEvent moveUpdateEvent = (MoveUpdateEvent) object;

            serverPlayer.moveUp = moveUpdateEvent.moveUp;
            serverPlayer.moveDown = moveUpdateEvent.moveDown;
            serverPlayer.moveLeft = moveUpdateEvent.moveLeft;
            serverPlayer.moveRight = moveUpdateEvent.moveRight;
        }
    }
}
