package de.saaslon.server.handlers;

import de.saaslon.global.PlayerUpdateEvent;
import de.saaslon.server.ServerFoundation;
import de.saaslon.server.supers.ServerPlayer;

public class PlayerUpdateHandler implements Runnable {

    public static final PlayerUpdateHandler INSTANCE = new PlayerUpdateHandler();

    private boolean running;

    public synchronized void start() {
        this.running = true;

        final Thread thread = new Thread(this);
        thread.start();
    }

    public void tick() {
        for(int i = 0; i < PlayerHandler.INSTANCE.getPlayers().size(); i++) {
            // Update server player
            final ServerPlayer serverPlayer = PlayerHandler.INSTANCE.getPlayers().get(i);
            serverPlayer.update();

            // Send update to all clients
            final PlayerUpdateEvent playerUpdateEvent = new PlayerUpdateEvent();
            playerUpdateEvent.username = serverPlayer.getUsername();
            playerUpdateEvent.x = serverPlayer.getX();
            playerUpdateEvent.y = serverPlayer.getY();

            ServerFoundation.instance.getServer().sendToAllUDP(playerUpdateEvent);
        }
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime();
        double amountOfTicks = 60;
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
