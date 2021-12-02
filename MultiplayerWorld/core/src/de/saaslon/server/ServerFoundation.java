package de.saaslon.server;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryonet.Server;
import de.saaslon.global.*;
import de.saaslon.server.handlers.PlayerUpdateHandler;
import de.saaslon.server.listeners.EventListener;
import de.saaslon.server.listeners.JoinListener;
import de.saaslon.server.listeners.LeaveListener;

import java.io.IOException;

public class ServerFoundation {

    public static ServerFoundation instance;

    private Server server;

    public static void main(String[] args) {
        ServerFoundation.instance = new ServerFoundation();
    }

    public ServerFoundation() {
        this.server = new Server();

        this.server.getKryo().register(JoinRequestEvent.class);
        this.server.getKryo().register(JoinResponseEvent.class);
        this.server.getKryo().register(PlayerAddEvent.class);
        this.server.getKryo().register(PlayerRemoveEvent.class);
        this.server.getKryo().register(PlayerUpdateEvent.class);
        this.server.getKryo().register(PlayerTransferEvent.class);
        this.server.getKryo().register(MoveUpdateEvent.class);
        this.server.getKryo().register(String.class);
        this.server.getKryo().register(Color.class);

        this.server.addListener(new JoinListener());
        this.server.addListener(new LeaveListener());
        this.server.addListener(new EventListener());

        PlayerUpdateHandler.INSTANCE.start();

        this.bindServer(6334, 6334);
    }

    public void bindServer(final int tcpPort, final int udpPort) {
        this.server.start();

        try {
            this.server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }
}
