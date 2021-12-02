package de.saaslon.server.handlers;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryonet.Connection;
import de.saaslon.global.PlayerAddEvent;
import de.saaslon.global.PlayerRemoveEvent;
import de.saaslon.global.PlayerTransferEvent;
import de.saaslon.server.ServerFoundation;
import de.saaslon.server.supers.ServerPlayer;

import java.util.LinkedList;

public class PlayerHandler {

    public static final PlayerHandler INSTANCE = new PlayerHandler();

    private final LinkedList<ServerPlayer> players;

    public PlayerHandler() {
        this.players = new LinkedList<>();
    }

    public ServerPlayer getPlayerByConnection(final Connection connection) {
        for(final ServerPlayer serverPlayer : this.players) {
            if(serverPlayer.getConnection() == connection) {
                return serverPlayer;
            }
        }

        return null;
    }

    public ServerPlayer getPlayerByUsername(final String username) {
        for(final ServerPlayer serverPlayer : this.players) {
            if(serverPlayer.getUsername().equals(username)) {
                return serverPlayer;
            }
        }

        return null;
    }

    public void update() {
        for(int i = 0; i < this.players.size(); i++) {
            this.players.get(i).update();
        }
    }

    public void addPlayer(final ServerPlayer serverPlayer) {
        for(ServerPlayer all : this.players) {
            final PlayerAddEvent playerAddEvent = new PlayerAddEvent();
            playerAddEvent.username = all.getUsername();
            playerAddEvent.color = all.getColor();
            playerAddEvent.x = all.getX();
            playerAddEvent.y = all.getY();

            serverPlayer.getConnection().sendTCP(playerAddEvent);
        }

        final PlayerAddEvent playerAddEvent = new PlayerAddEvent();
        playerAddEvent.username = serverPlayer.getUsername();
        playerAddEvent.color = serverPlayer.getColor();
        playerAddEvent.x = serverPlayer.getX();
        playerAddEvent.y = serverPlayer.getY();

        ServerFoundation.instance.getServer().sendToAllTCP(playerAddEvent);

        this.players.add(serverPlayer);
    }

    public void removePlayer(final ServerPlayer serverPlayer) {
        this.players.remove(serverPlayer);

        final PlayerRemoveEvent playerRemoveEvent = new PlayerRemoveEvent();
        playerRemoveEvent.username = serverPlayer.getUsername();

        ServerFoundation.instance.getServer().sendToAllTCP(playerRemoveEvent);
    }

    public LinkedList<ServerPlayer> getPlayers() {
        return players;
    }
}
