package com.github.kill05.goldmountain;

import com.github.kill05.goldmountain.commands.CommandHandler;
import com.github.kill05.goldmountain.player.PlayerCostume;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2f;

public class GMServer {

    public static final int TARGET_TPS = 20;
    public static final Logger logger = LogManager.getLogger("Server");

    private Thread serverThread;
    private ServerConnection serverConnection;
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

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
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

        /*
        for(int i = 1; i < 11; i++) {
            sendTestPlayer((short) i, new Vector2f(-0.5f + i, 0.5f), PlayerCostume.values()[i], (short) 0x1400);
        }
        */

        //serverConnection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0080 0061 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));
        //serverConnection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0780 0761 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));
        //serverConnection.sendPacket(new TestPacket("03 4700 0000 0000 2b00 0000 0101 007e 0000  00c70253 02c70253 02c70253 02c70253 0214 003e  0000  00000047 9700 00000000 00e8 03"));
        //serverConnection.sendPacket(new TestPacket("01feff69de5900cc008c00cc008c00cc008c00cc008c002d00ffffffff9700ffffffff00000000e8030dcd4100000101000c000000910098009100980091009800910098001400ffffffff0000ffffffff00000000e803"));
        //serverConnection.sendPacket(new PacketOutServerPlayerUpdate());
    }

    public void sendTestPlayer(short id, Vector2f loc, PlayerCostume costume, short yaw) {
        PacketInOutPlayerUpdate packet = new PacketInOutPlayerUpdate();

        packet.setPlayerId(id);
        packet.setMetadata(0x78000000);

        packet.setCostume(costume);
        packet.setClock((byte) 0x00);
        packet.setCurrentAction(0xffff_ffff);
        packet.setUnknown_1(0xffff_ffff);

        packet.setYaw(yaw);
        packet.setUnknown_2((short) 0x4300);

        packet.setCurrentPos(loc);
        packet.setFuturePos(loc);
        packet.setLocation3(loc);
        packet.setLocation4(loc);

        serverConnection.sendPacket(packet);
    }


    public void shutdown() {
        logger.info("Closing server...");

        try {
            serverConnection.shutdown();
        } catch (InterruptedException e) {
            logger.warn("Failed to gracefully shutdown server.", e);
        }

        logger.info("Bye!");
    }


    public Thread getServerThread() {
        return serverThread;
    }

    public ServerConnection getConnection() {
        return serverConnection;
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
