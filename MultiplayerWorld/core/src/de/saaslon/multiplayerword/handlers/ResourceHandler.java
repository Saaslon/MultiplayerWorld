package de.saaslon.multiplayerword.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResourceHandler {

    public final static ResourceHandler INSTANCE = new ResourceHandler();

    public final Texture grass = new Texture(Gdx.files.internal("grass.png"));

    public final Animation<TextureAtlas.AtlasRegion> playerIdle =
            new Animation<>(1 / 60F, new TextureAtlas(Gdx.files.internal("player_idle.atlas")).getRegions());

    public final Animation<TextureAtlas.AtlasRegion> playerWalk =
            new Animation<>(1 / 60F, new TextureAtlas(Gdx.files.internal("player_walk.pack")).getRegions());
}
