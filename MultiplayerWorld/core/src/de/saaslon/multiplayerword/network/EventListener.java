package de.saaslon.multiplayerword.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.saaslon.global.JoinResponseEvent;
import de.saaslon.global.PlayerAddEvent;
import de.saaslon.global.PlayerRemoveEvent;
import de.saaslon.global.PlayerUpdateEvent;
import de.saaslon.multiplayerword.MultiplayerWorld;
import de.saaslon.multiplayerword.handlers.PlayerHandler;
import de.saaslon.multiplayerword.screens.IngameScreen;
import de.saaslon.multiplayerword.supers.Player;

public class EventListener extends Listener {

    @Override
    public void received(Connection connection, final Object object) {

        // Join success
        if(object instanceof JoinResponseEvent) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    MultiplayerWorld.getInstance().setScreen(IngameScreen.INSTANCE);
                }
            });
        }
        // Player add
        else if(object instanceof PlayerAddEvent) {

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    PlayerAddEvent playerAddEvent = (PlayerAddEvent) object;

                    final Player player = new Player(playerAddEvent.username, playerAddEvent.color);
                    player.getPosition().x = playerAddEvent.x;
                    player.getPosition().y = playerAddEvent.y;
                    player.getServerPosition().x = playerAddEvent.x;
                    player.getServerPosition().y = playerAddEvent.y;

                    PlayerHandler.INSTANCE.addPlayer(player);
                }
            });
        }
        // Player remove
        else if(object instanceof PlayerRemoveEvent) {
            PlayerHandler.INSTANCE.removePlayer(PlayerHandler.INSTANCE.getPlayerByUsername(((PlayerRemoveEvent) object).username));
        }
        // Player update
        else if(object instanceof PlayerUpdateEvent) {
            final PlayerUpdateEvent playerUpdateEvent = (PlayerUpdateEvent) object;

            final Player player = PlayerHandler.INSTANCE.getPlayerByUsername(playerUpdateEvent.username);
            if(player == null) return;

            player.getServerPosition().set(playerUpdateEvent.x, playerUpdateEvent.y);
        }


        super.received(connection, object);
    }
}
