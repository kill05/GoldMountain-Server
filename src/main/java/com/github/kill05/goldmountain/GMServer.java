package com.github.kill05.goldmountain;

import com.github.kill05.goldmountain.commands.CommandHandler;
import com.github.kill05.goldmountain.dimension.entity.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PlayerController;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

public class GMServer {

    public static final int TARGET_TPS = 15;
    public static final Logger logger = LogManager.getLogger(GMServer.class);

    private Thread serverThread;
    private PlayerController playerController;
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
                this.playerController = new PlayerController(this);
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
        //commandHandler.executeCommand("/test2 0000 4368b73f");
        commandHandler.processInput();
    }


    public void sendTestPlayer(short id, Vector2f loc1, Vector2f loc2, Vector2f loc3, Vector2f loc4) {
        PacketInOutPlayerUpdate packet = new PacketInOutPlayerUpdate();

        packet.setEntityId(id);
        packet.setTotalLevel(0x0000_0000);

        packet.setCostume(PlayerCostume.DEFAULT);
        packet.setUnknown_2((byte) 0x00);
        packet.setUnknown_0(0xffff_ffff);
        packet.setTargetTileId(0xffff_ffff);

        packet.setSpeed((short) 0x0040);
        packet.setUnknown_3((short) 0x4300);

        packet.setCheckpoints(new Vector2f[]{
                loc1, loc2, loc3, loc4
        });

        playerController.broadcastPacket(packet);
    }


    public void shutdown() {
        logger.info("Closing server...");

        try {
            playerController.shutdown();
        } catch (InterruptedException e) {
            logger.warn("Failed to gracefully shutdown server.", e);
        }

        logger.info("Bye!");
    }


    public Thread getServerThread() {
        return serverThread;
    }

    public PlayerController getPlayerController() {
        return playerController;
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
