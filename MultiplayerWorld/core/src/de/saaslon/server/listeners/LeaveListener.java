package de.saaslon.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.saaslon.server.handlers.PlayerHandler;
import de.saaslon.server.supers.ServerPlayer;

import java.util.LinkedList;

public class LeaveListener extends Listener {

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);

        final ServerPlayer leavePlayer = PlayerHandler.INSTANCE.getPlayerByConnection(connection);

        if(leavePlayer == null) return;

        PlayerHandler.INSTANCE.removePlayer(leavePlayer);
    }
}
