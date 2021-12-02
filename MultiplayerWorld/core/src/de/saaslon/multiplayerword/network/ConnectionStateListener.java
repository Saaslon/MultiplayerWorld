package de.saaslon.multiplayerword.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.saaslon.multiplayerword.MultiplayerWorld;
import de.saaslon.multiplayerword.screens.ConnectScreen;
import de.saaslon.multiplayerword.screens.IngameScreen;

public class ConnectionStateListener extends Listener {

    @Override
    public void disconnected(Connection connection) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MultiplayerWorld.getInstance().setScreen(ConnectScreen.INSTANCE);
                ConnectScreen.INSTANCE.getErrorLabel().setText("Connection lost!");
            }
        });
        super.disconnected(connection);
    }
}
