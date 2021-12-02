package de.saaslon.multiplayerword.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.saaslon.global.MoveUpdateEvent;
import de.saaslon.multiplayerword.MultiplayerWorld;

public class MoveUpdateHandler implements Runnable {

    public static final MoveUpdateHandler INSTANCE = new MoveUpdateHandler();

    private boolean moveUp, moveDown, moveLeft, moveRight;

    private boolean running;

    public synchronized void start() {
        this.running = true;

        final Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        this.running = false;
    }

    public void tick() {
        boolean w, s, a, d;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            w = true;
        } else {
            w = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            s = true;
        } else {
            s = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            a = true;
        } else {
            a = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            d = true;
        } else {
            d = false;
        }

        final boolean sameMovement = this.movementChanged(w, s, a, d);

        if(!sameMovement) {
            this.moveUp = w;
            this.moveDown = s;
            this.moveLeft = a;
            this.moveRight = d;

            final MoveUpdateEvent moveUpdateEvent = new MoveUpdateEvent();
            moveUpdateEvent.moveUp = this.moveUp;
            moveUpdateEvent.moveDown = this.moveDown;
            moveUpdateEvent.moveLeft = this.moveLeft;
            moveUpdateEvent.moveRight = this.moveRight;

            MultiplayerWorld.getInstance().getClient().sendTCP(moveUpdateEvent);
            System.out.println("sended");
        }
    }

    public boolean movementChanged(final boolean moveUp, final boolean moveDown, final boolean moveLeft, final boolean moveRight) {
        return moveUp == this.moveUp && moveDown == this.moveDown && moveLeft == this.moveLeft && moveRight == this.moveRight;
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime();
        double amountOfTicks = 30;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (this.running) {

            try {
                Thread.sleep((long) (60F / amountOfTicks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;

            while (delta > 0) {
                tick();
                delta--;
            }
        }
    }
}
