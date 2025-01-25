package com.github.kill05.goldmountain.server.connection;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.dimension.DimensionType;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.connection.packets.Packet;
import com.github.kill05.goldmountain.connection.packets.PacketRegistry;
import com.github.kill05.goldmountain.connection.packets.out.AssignPlayerIdPacket;
import com.github.kill05.goldmountain.connection.pipeline.PacketDecoder;
import com.github.kill05.goldmountain.connection.pipeline.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class ConnectionController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionController.class);
    public static final int FIRST_PLAYER_ID = 1;

    private final GMServer server;
    private final PacketRegistry packetRegistry;
    private final ConcurrentMap<Channel, ServerPlayer> players;

    private final Channel serverChannel;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public ConnectionController(GMServer server) {
        this.server = server;
        this.packetRegistry = new PacketRegistry();
        this.players = new ConcurrentHashMap<>();

        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ConnectionController controller = ConnectionController.this;
                        ServerPlayer player = new ServerPlayer(controller, (short) getAvailableId(), channel);
                        PlayerConnection connection = player.getConnection();

                        channel.pipeline()
                                .addLast("packet_decoder", new PacketDecoder(packetRegistry))
                                .addLast("packet_encoder", new PacketEncoder(packetRegistry))
                                .addLast("packet_handler", new PacketHandler(connection))
                                .addLast("channel_listener", new ChannelListener());

                        players.put(channel, player);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .bind(ConnectionConstants.PORT)
                .channel();
    }

    private class ChannelListener extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            handleConnect(ctx.channel());
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            handleDisconnect(ctx.channel());
            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if (cause instanceof IOException) {
                SocketAddress address = ctx.channel().remoteAddress();
                GMServer.LOGGER.info("Player (address: {}) lost connection: {}", address, cause);

                handleDisconnect(ctx.channel());
                return;
            }

            super.exceptionCaught(ctx, cause);
        }
    }


    //todo: process incoming packet queue
    public void tick() {

    }

    public void shutdown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        bossGroup.shutdownGracefully().addListener(future -> latch.countDown());
        workerGroup.shutdownGracefully().addListener(future -> latch.countDown());

        latch.await();
    }


    private void handleConnect(@NotNull Channel channel) {
        ServerPlayer player = getPlayer(channel);
        player.getConnection().sendPacket(new AssignPlayerIdPacket(player.getId()));
        player.setDimension(DimensionType.SPAWN);

        SocketAddress address = channel.remoteAddress();
        GMServer.LOGGER.info("Player (id: {}, address: {}) connected.", player.getId(), address);
    }

    private void handleDisconnect(@NotNull Channel channel) {
        ServerPlayer player = players.remove(channel);
        if (player == null) {
            LOGGER.warn("handleDisconnect called twice!");
            return;
        }

        //todo: submit player remove task to main thread
        player.remove();

        SocketAddress address = channel.remoteAddress();
        GMServer.LOGGER.info("Player (id: {}, address: {}) disconnected.", player.getId(), address);
    }

    private int getAvailableId() throws IOException {
        short id = FIRST_PLAYER_ID;
        while (true) {
            if (getPlayerFromId(id) == null) return id;
            if (id == Short.MAX_VALUE) throw new IOException("No more available ids.");
            id++;
        }
    }

    public void broadcastPacket(Packet packet) {
        for (ServerPlayer player : players.values()) {
            player.getConnection().sendPacket(packet);
        }
    }


    public @NotNull Collection<ServerPlayer> getPlayers() {
        return players.values();
    }

    public @NotNull ServerPlayer getPlayer(@NotNull Channel channel) {
        return players.get(channel);
    }

    public @Nullable ServerPlayer getPlayerFromId(int id) {
        for (ServerPlayer player : players.values()) {
            if (player.getId() == id) return player;
        }

        return null;
    }


    public @NotNull Channel getServerChannel() {
        return serverChannel;
    }

    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public GMServer getServer() {
        return server;
    }
}
