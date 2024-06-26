package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.entity.ServerPlayer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutAssignPlayerId;
import com.github.kill05.goldmountain.protocol.pipeline.PacketDecoder;
import com.github.kill05.goldmountain.protocol.pipeline.PacketEncoder;
import com.github.kill05.goldmountain.protocol.pipeline.PacketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class PlayerController {

    public static final int PORT = 5575;
    public static final short MAGIC_BYTES = 0x1764;

    private final GMServer server;
    private final PacketRegistry packetRegistry;
    private final Channel serverChannel;
    private final ConcurrentMap<Channel, ServerPlayer> players;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public PlayerController(GMServer server) {
        this.server = server;
        this.players = new ConcurrentHashMap<>();
        this.packetRegistry = PacketRegistry.instance();

        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        PlayerController controller = PlayerController.this;
                        ServerPlayer player = new ServerPlayer(controller, (short) getAvailableId(), channel);
                        PlayerConnection connection = player.getConnection();

                        channel.pipeline()
                                .addLast("packet_decoder", new PacketDecoder(controller))
                                .addLast("packet_encoder", new PacketEncoder(controller))
                                .addLast("packet_handler", new PacketHandler(connection))
                                .addLast("channel_listener", new ChannelListener());

                        players.put(channel, player);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .bind(PORT).channel();
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
            if(cause instanceof IOException) {
                SocketAddress address = ctx.channel().remoteAddress();
                GMServer.logger.info(String.format("Player (address: %s) lost connection: %s", address, cause));

                handleDisconnect(ctx.channel());
                return;
            }

            super.exceptionCaught(ctx, cause);
        }
    }


    public void tick() {
        for (ServerPlayer player : players.values()) {
            player.tick();
        }
    }


    public void shutdown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        bossGroup.shutdownGracefully().addListener(future -> latch.countDown());
        workerGroup.shutdownGracefully().addListener(future -> latch.countDown());

        latch.await();
    }


    private void handleConnect(Channel channel) {
        ServerPlayer player = getPlayer(channel);
        player.getConnection().sendPacket(new PacketOutAssignPlayerId(player.getId()));

        SocketAddress address = channel.remoteAddress();
        GMServer.logger.info(String.format("Player (id: %s, address: %s) connected.", player.getId(), address));
    }

    private void handleDisconnect(Channel channel) {
        ServerPlayer player = getPlayer(channel);
        players.remove(channel);

        SocketAddress address = channel.remoteAddress();
        GMServer.logger.info(String.format("Player (id: %s, address: %s) disconnected.", player.getId(), address));
    }

    private int getAvailableId() throws IOException {
        short id = 0;
        while (true) {
            if(getPlayer(id) == null) return id;
            if(id == Short.MAX_VALUE) throw new IOException("No more available ids.");
            id++;
        }
    }

    public void broadcastPacket(Packet packet) {
        for(ServerPlayer player : players.values()) {
            player.getConnection().sendPacket(packet);
        }
    }


    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public Channel getServerChannel() {
        return serverChannel;
    }

    public Collection<ServerPlayer> getPlayers() {
        return players.values();
    }

    public ServerPlayer getPlayer(Channel channel) {
        return players.get(channel);
    }

    public @Nullable ServerPlayer getPlayer(int id) {
        for (ServerPlayer player : players.values()) {
            if(player.getId() == id) return player;
        }

        return null;
    }


    public GMServer getServer() {
        return server;
    }

}
