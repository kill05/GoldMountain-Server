package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutAssignPlayerId;
import com.github.kill05.goldmountain.protocol.pipeline.PacketDecoder;
import com.github.kill05.goldmountain.protocol.pipeline.PacketEncoder;
import com.github.kill05.goldmountain.protocol.pipeline.PacketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ServerConnection {

    public static final int PORT = 5575;
    public static final short MAGIC_BYTES = 0x1764;

    private final GMServer server;
    private final PacketRegistry packetRegistry;
    private final Channel serverChannel;
    private final List<Channel> clientChannels;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public PacketInOutPlayerUpdate lastUpdate;

    public ServerConnection(GMServer server) {
        this.server = server;
        this.clientChannels = Collections.synchronizedList(new ArrayList<>());
        this.packetRegistry = PacketRegistry.instance();

        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                .addLast("packet_splitter", new PacketDecoder())
                                .addLast("packet_encoder", new PacketEncoder())
                                .addLast("packet_handler", new PacketHandler(ServerConnection.this))
                                .addLast("channel_listener", new ChannelListener());

                        clientChannels.add(channel);
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
            GMServer.logger.info(String.format("Player (ip: %s) connected.", ctx.channel().remoteAddress()));

            sendPacket(new PacketOutAssignPlayerId(0x0001));
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            GMServer.logger.info(String.format("Player (ip: %s) disconnected.", ctx.channel().remoteAddress()));
            clientChannels.remove(ctx.channel());

            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            if(cause instanceof IOException) {
                GMServer.logger.info(String.format("Player (ip: %s) lost connection: %s", ctx.channel().remoteAddress(), cause));
                return;
            }

            super.exceptionCaught(ctx, cause);
        }
    }


    public void shutdown() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        bossGroup.shutdownGracefully().addListener(future -> latch.countDown());
        workerGroup.shutdownGracefully().addListener(future -> latch.countDown());

        latch.await();
    }


    public void sendPacket(Packet packet) {
        for(Channel channel : clientChannels) {
            channel.writeAndFlush(packet);
        }
    }


    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public Channel getServerChannel() {
        return serverChannel;
    }

    public List<Channel> getClientChannels() {
        return clientChannels;
    }


    public GMServer getServer() {
        return server;
    }


}
