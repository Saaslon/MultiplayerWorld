package de.saaslon.server.supers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {

    public static final Array<Color> COLORS;

    static {
        COLORS = new Array<>();
        COLORS.add(Color.RED);
        COLORS.add(Color.BLUE);
        COLORS.add(Color.GREEN);
        COLORS.add(Color.GOLD);
        COLORS.add(Color.MAGENTA);
        COLORS.add(Color.YELLOW);
    }

    private final String username;
    private Color color;
    private final Connection connection;

    public boolean moveUp, moveDown, moveLeft, moveRight;

    private float speed;

    private float x;
    private float y;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;

        this.color = Color.WHITE;

        this.speed = 5F;
    }

    public void update() {
        if(this.moveLeft) {
            this.x -= this.speed;
        } else if(this.moveRight) {
            this.x += this.speed;
        }

        if(this.moveUp) {
            this.y += this.speed;
        } else if(this.moveDown) {
            this.y -= this.speed;
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getUsername() {
        return username;
    }

    public Color getColor() {
        return color;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
