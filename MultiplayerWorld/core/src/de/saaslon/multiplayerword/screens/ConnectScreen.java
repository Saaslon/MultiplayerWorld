package de.saaslon.multiplayerword.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.kryonet.Client;
import de.saaslon.global.*;
import de.saaslon.multiplayerword.MultiplayerWorld;
import de.saaslon.multiplayerword.handlers.LabelHandler;
import de.saaslon.multiplayerword.network.ConnectionStateListener;
import de.saaslon.multiplayerword.network.EventListener;

import java.io.IOException;

public class ConnectScreen implements Screen {

    public static final ConnectScreen INSTANCE = new ConnectScreen();

    private final Stage stage;
    private final Table root;

    private final TextField ipAddressLabel;
    private final TextField portLabel;
    private final TextField usernameLabel;

    private final TextButton connectButton;

    private final Label errorLabel;

    public static void testMethod() {
        System.out.println("Das ist eine Test-Methode für das Video :D");
    }

    public ConnectScreen() {
        this.stage = new Stage();
        this.stage.getViewport().setCamera(MultiplayerWorld.getInstance().getCamera());

        this.root = new Table();
        this.root.setBounds(0, 0, 800, 600);

        final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        this.ipAddressLabel = new TextField("localhost", skin);
        this.portLabel = new TextField("6334", skin);
        this.usernameLabel = new TextField("Username", skin);

        this.connectButton = new TextButton("Connect", skin);
        this.connectButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Connect
                final Client client = new Client();

                client.addListener(new ConnectionStateListener());
                client.addListener(new EventListener());

                client.getKryo().register(JoinRequestEvent.class);
                client.getKryo().register(JoinResponseEvent.class);
                client.getKryo().register(PlayerAddEvent.class);
                client.getKryo().register(PlayerRemoveEvent.class);
                client.getKryo().register(PlayerUpdateEvent.class);
                client.getKryo().register(PlayerTransferEvent.class);
                client.getKryo().register(MoveUpdateEvent.class);
                client.getKryo().register(String.class);
                client.getKryo().register(Color.class);

                try {
                    client.start();
                    client.connect(15000, ipAddressLabel.getText(), Integer.parseInt(portLabel.getText()),  Integer.parseInt(portLabel.getText()));
                } catch (Exception e) {
                    errorLabel.setText(e.getMessage());
                    return super.touchDown(event, x, y, pointer, button);
                }

                // Success
                MultiplayerWorld.getInstance().setClient(client);

                JoinRequestEvent joinRequestEvent = new JoinRequestEvent();
                joinRequestEvent.username = usernameLabel.getText();

                client.sendTCP(joinRequestEvent);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        this.errorLabel = LabelHandler.INSTANCE.createLabel(null, 16, Color.RED);

        this.stage.addActor(this.root);

        this.setToDefault();
    }

    public void setToDefault() {
        this.root.clear();
        this.root.add(this.ipAddressLabel).width(250).row();
        this.root.add(this.portLabel).width(250).padTop(25).row();
        this.root.add(this.usernameLabel).width(250).padTop(25).row();
        this.root.add(this.connectButton).size(250, 50).padTop(100).row();
        this.root.add(this.errorLabel).padTop(50);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        this.stage.draw();
        this.stage.act(delta);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}
