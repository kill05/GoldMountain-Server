package com.github.kill05.goldmountain;

import com.github.kill05.goldmountain.commands.CommandHandler;
import com.github.kill05.goldmountain.dimension.DimensionController;
import com.github.kill05.goldmountain.dimension.entity.PlayerCostume;
import com.github.kill05.goldmountain.dimension.entity.player.FakePlayer;
import com.github.kill05.goldmountain.dimension.entity.player.PlayerEntity;
import com.github.kill05.goldmountain.protocol.ConnectionController;
import com.github.kill05.goldmountain.protocol.packets.io.PlayerUpdatePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

public class GMServer {

    public static final int TARGET_TPS = 15;
    public static final Logger logger = LogManager.getLogger(GMServer.class);

    private Thread serverThread;
    private ConnectionController connectionController;
    private DimensionController dimensionController;
    private CommandHandler commandHandler;
    private long currentTick;
    private float tps;

    public GMServer() {
        init();
    }


    private void init() {
        long start = System.currentTimeMillis();
        logger.info("Starting server thread...");

        this.serverThread = new Thread(null, () -> {
            try {
                this.connectionController = new ConnectionController(this);
                this.dimensionController = new DimensionController();
                this.commandHandler = new CommandHandler(this);
            } catch (Exception e) {
                logger.error("There was an error while loading the server.", e);
                System.exit(-1);
            }

            logger.info(String.format("Server started! (took %sms)", System.currentTimeMillis() - start));
            startGameLoop();
        }, "Server Thread");

        serverThread.start();

        //Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }



    private void startGameLoop() {
        final double timeU = 1e9 / TARGET_TPS;
        long initialTime = System.nanoTime();
        double deltaT = 0;

        while (true) {
            long currentTime = System.nanoTime();
            deltaT += (currentTime - initialTime) / timeU;
            initialTime = currentTime;

            if (deltaT >= 1) {
                deltaT = 0;

                try {
                    tick();
                } catch (Throwable e) {
                    logger.error("An exception was caught while ticking server.", e);
                }

                tps = Math.min((float) (1e9 / (System.nanoTime() - initialTime)), TARGET_TPS);
                currentTick++;
            }
        }
    }

    private void tick() {
        dimensionController.preTick();

        //commandHandler.executeCommand("/test2 0000 4368b73f");
        connectionController.tick();
        dimensionController.tick();
        commandHandler.processInput();
    }


    public void sendFakePlayer(int id, short speed, PlayerCostume costume, Vector2f loc) {
        sendFakePlayer(id, speed, costume, loc, loc, loc, loc);
    }

    public void sendFakePlayer(int id, short speed, PlayerCostume costume, Vector2f loc1, Vector2f loc2, Vector2f loc3, Vector2f loc4) {
        PlayerEntity fakePlayer = new FakePlayer(this, id);
        fakePlayer.setDisplayCostume(costume);
        fakePlayer.setSpeed(speed);
        fakePlayer.getCheckpoints()[0] = loc1;
        fakePlayer.getCheckpoints()[1] = loc2;
        fakePlayer.getCheckpoints()[2] = loc3;
        fakePlayer.getCheckpoints()[3] = loc4;

        PlayerUpdatePacket packet = new PlayerUpdatePacket(fakePlayer);
        connectionController.broadcastPacket(packet);
    }


    public void shutdown() {
        logger.info("Closing server...");

        try {
            connectionController.shutdown();
        } catch (InterruptedException e) {
            logger.warn("Failed to gracefully shutdown server.", e);
        }

        logger.info("Bye!");
    }


    public ConnectionController getPlayerController() {
        return connectionController;
    }

    public DimensionController getDimensionController() {
        return dimensionController;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public float getTps() {
        return tps;
    }


}
