package de.saaslon.multiplayerword.supers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import de.saaslon.multiplayerword.handlers.FontSizeHandler;
import de.saaslon.multiplayerword.handlers.ResourceHandler;

public class Player {

    private final Vector2 position;
    private final Vector2 serverPosition;
    private final Vector2 distance;

    private final Color color;

    private final String username;

    private final GlyphLayout layout;
    private final BitmapFont font;

    private float pastTime;

    public Player(final String username, final Color color) {
        this.position = new Vector2();
        this.serverPosition = new Vector2();

        this.distance = new Vector2();

        this.username = username;
        this.color = color;

        this.layout = new GlyphLayout();
        this.font = FontSizeHandler.INSTANCE.getFont(18, Color.BLACK);
    }

    public void render(final Batch batch) {

        // Color player
        batch.setColor(this.color);

            TextureRegion frame = ResourceHandler.INSTANCE.playerIdle.getKeyFrame(this.pastTime, true);
            batch.draw(frame, this.position.x, this.position.y, frame.getRegionWidth(), frame.getRegionHeight());

        batch.setColor(Color.WHITE);

        this.layout.setText(this.font, this.username);

        this.font.draw(batch, this.username, this.position.x + frame.getRegionWidth() / 2F - this.layout.width / 2, this.position.y + frame.getRegionHeight() + 10);
    }

    public void update(final float delta) {
        this.pastTime += delta;

        final Vector2 interpolate = this.position.interpolate(this.serverPosition, 0.2F, Interpolation.linear);

        this.distance.x = interpolate.x - serverPosition.x;
        this.distance.y = interpolate.y - serverPosition.y;
    }

    public String getUsername() {
        return username;
    }

    public Vector2 getServerPosition() {
        return serverPosition;
    }

    public Vector2 getPosition() {
        return position;
    }
}
