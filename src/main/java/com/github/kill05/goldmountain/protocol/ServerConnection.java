package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.pipeline.PacketEncoder;
import com.github.kill05.goldmountain.protocol.pipeline.PacketDecoder;
import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.pipeline.PacketHandler;

import java.util.List;

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
        this.packetRegistry = new PacketRegistry(this);
        this.clientChannels = Lists.newArrayList();

        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                .addLast("splitter", new PacketDecoder(ServerConnection.this))
                                .addLast("packet_encoder", new PacketEncoder(packetRegistry))
                                .addLast("packet_handler", new PacketHandler(ServerConnection.this));

                        clientChannels.add(channel);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(PORT).channel();
    }

    public void shutdownNow() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
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
