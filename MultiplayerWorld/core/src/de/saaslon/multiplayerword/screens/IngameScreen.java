package de.saaslon.multiplayerword.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.saaslon.multiplayerword.MultiplayerWorld;
import de.saaslon.multiplayerword.handlers.MoveUpdateHandler;
import de.saaslon.multiplayerword.handlers.PlayerHandler;
import de.saaslon.multiplayerword.handlers.ResourceHandler;
import de.saaslon.multiplayerword.supers.Player;

import java.awt.*;

public class IngameScreen implements Screen {

    public static final IngameScreen INSTANCE = new IngameScreen();

    private final SpriteBatch batch;

    public IngameScreen() {
        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(MultiplayerWorld.getInstance().getCamera().combined);
    }

    @Override
    public void show() {
        MoveUpdateHandler.INSTANCE.start();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        this.batch.begin();

        // Draw background grass
        for(int x = 0; x < Gdx.graphics.getWidth() / ResourceHandler.INSTANCE.grass.getWidth(); x++) {
            for(int y = 0; y < Gdx.graphics.getHeight() / ResourceHandler.INSTANCE.grass.getHeight(); y++) {
                this.batch.draw(ResourceHandler.INSTANCE.grass, ResourceHandler.INSTANCE.grass.getWidth() * x, ResourceHandler.INSTANCE.grass.getHeight() * y);
            }
        }

        PlayerHandler.INSTANCE.render(this.batch);
        PlayerHandler.INSTANCE.update(delta);

        this.batch.end();
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
        MoveUpdateHandler.INSTANCE.stop();
    }

    @Override
    public void dispose() {

    }
}
