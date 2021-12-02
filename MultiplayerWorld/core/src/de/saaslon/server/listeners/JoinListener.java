package de.saaslon.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.saaslon.global.JoinRequestEvent;
import de.saaslon.global.JoinResponseEvent;
import de.saaslon.server.handlers.PlayerHandler;
import de.saaslon.server.supers.ServerPlayer;

import java.util.Random;

public class JoinListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {

        // Join request
        if (object instanceof JoinRequestEvent) {
            final JoinRequestEvent joinRequestEvent = (JoinRequestEvent) object;

            final ServerPlayer serverPlayer = new ServerPlayer(joinRequestEvent.username, connection);

            final Random random = new Random();

            serverPlayer.setX(random.nextInt(700));
            serverPlayer.setY(random.nextInt(500));

            serverPlayer.setColor(ServerPlayer.COLORS.get(random.nextInt(ServerPlayer.COLORS.size)));

            // Name already in use
            if(PlayerHandler.INSTANCE.getPlayerByUsername(((JoinRequestEvent) object).username) != null) {
                return;
            }

            PlayerHandler.INSTANCE.addPlayer(serverPlayer);

            final JoinResponseEvent joinResponseEvent = new JoinResponseEvent();
            connection.sendTCP(joinResponseEvent);
        }

        super.received(connection, object);
    }
}
