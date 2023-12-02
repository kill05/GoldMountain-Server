package com.github.kill05.goldmountain;

import com.github.kill05.goldmountain.commands.CommandHandler;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.TestPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GMServer {

    public static final int TARGET_TPS = 20;
    public static final Logger logger = LogManager.getLogger("Server");

    private static GMServer instance;
    private Thread serverThread;
    private ServerConnection serverConnection;
    private CommandHandler commandHandler;
    private int currentTick;
    private float tps;

    private GMServer() {
        init();
    }


    public static GMServer instance() {
        return instance != null ? instance : (instance = new GMServer());
    }


    private void init() {
        long start = System.currentTimeMillis();
        logger.info("Starting server thread...");

        this.serverThread = new Thread(null, () -> {
            try {
                this.serverConnection = new ServerConnection(this);
                this.commandHandler = new CommandHandler(this);
            } catch (Exception e) {
                logger.error("There was an error while loading the server.", e);
                System.exit(-1);
            }

            logger.info(String.format("Server started! (took %sms)", System.currentTimeMillis() - start));
            startGameLoop();
        }, "Server Thread");

        serverThread.start();
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
        commandHandler.processInput();
        //if (currentTick % 2 == 0) serverConnection.sendPacket(new TestPacket("5d00000001feff69de5900cc008c00cc008c00cc008c00cc008c002d00ffffffff9700ffffffff00000000e8030dcd4100000101000c000000910098009100980091009800910098001400ffffffff0000ffffffff00000000e803"));
        //if (currentTick % 2 == 1) serverConnection.sendPacket(new TestPacket("5d00000001feff69fe5900cf008c00cc008c00cc008c00cc008c002d00ffffffff9800ffffffff00000000e8030dcd4100000101000c000010910098009100980091009800910098001400ffffffff9900ffffffff00000000e803"));
        //serverConnection.sendPacket(new PacketOutServerPlayerUpdate());
    }


    public void shutdown() throws InterruptedException {
        logger.info("Shutting down...");
        serverConnection.shutdownNow();
    }


    public ServerConnection getConnection() {
        return serverConnection;
    }

    public CommandHandler getCommandReader() {
        return commandHandler;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public float getTps() {
        return tps;
    }


}
