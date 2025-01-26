package com.github.kill05.goldmountain.proxy.connection;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.connection.ProxyPacketRegistry;
import com.github.kill05.goldmountain.connection.packet.PacketRegistry;
import com.github.kill05.goldmountain.connection.pipeline.PacketDecoder;
import com.github.kill05.goldmountain.connection.pipeline.PacketEncoder;
import com.github.kill05.goldmountain.proxy.GMProxy;
import com.github.kill05.goldmountain.proxy.gui.packet.PacketPanel;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProxyConnection {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProxyConnection.class);
    public static final InetSocketAddress DESTINATION_ADDRESS = new InetSocketAddress("192.168.1.30", ConnectionConstants.PORT);

    private final PacketRegistry packetRegistry;
    private final Lock lock;

    private final GMProxy proxy;
    private final Channel serverChannel;
    private Channel clientToProxyChannel;
    private Channel proxyToServerChannel;
    private final EventLoopGroup clientGroup;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public ProxyConnection(GMProxy proxy) {
        this.proxy = proxy;
        this.packetRegistry = new ProxyPacketRegistry();
        this.lock = new ReentrantLock();

        this.clientGroup = new NioEventLoopGroup();
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                .addLast("packet_decoder", new PacketDecoder(packetRegistry))
                                .addLast("packet_encoder", new PacketEncoder(packetRegistry))
                                .addLast(new CloseHandler("client"))
                                .addLast(new ChannelDuplexHandler() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        try {
                                            lock.lock();
                                            if (proxyToServerChannel != null) {
                                                LOGGER.info("Refusing connection because another client is already connected.");
                                                ctx.channel().disconnect();
                                                return;
                                            }

                                            proxyToServerChannel = connectToDestinationServer(ctx.channel());
                                            clientToProxyChannel = ctx.channel();

                                            // Add handler after connection to destination server has been established
                                            PacketPanel panel = proxy.getFrame().getClientToProxyPanel();
                                            ctx.channel().pipeline()
                                                    .addLast("packet_handler", new ProxyPacketHandler(proxyToServerChannel, panel));
                                        } finally {
                                            lock.unlock();
                                        }

                                        super.channelActive(ctx);
                                    }
                                });

                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .bind(ConnectionConstants.PORT)
                .channel();
    }

    public void shutdown() {
        try {
            CountDownLatch latch = new CountDownLatch(3);

            clientGroup.shutdownGracefully().addListener(future -> latch.countDown());
            bossGroup.shutdownGracefully().addListener(future -> latch.countDown());
            workerGroup.shutdownGracefully().addListener(future -> latch.countDown());

            latch.await();
        } catch (Exception e) {
            LOGGER.warn("Failed to shutdown proxy connection.", e);
        }
    }

    /**
     * Called after the client connected to the proxy, forwards the connection to the destination server
     *
     * @param clientChannel the channel of the client that connected
     */
    private Channel connectToDestinationServer(Channel clientChannel) {
        try {
            LOGGER.info("Client connected. Connecting to destination server...");

            return new Bootstrap().group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            PacketPanel panel = proxy.getFrame().getServerToProxyPanel();

                            channel.pipeline()
                                    .addLast("packet_decoder", new PacketDecoder(packetRegistry))
                                    .addLast("packet_encoder", new PacketEncoder(packetRegistry))
                                    .addLast("packet_handler", new ProxyPacketHandler(clientChannel, panel))
                                    .addLast(new CloseHandler("destination server"))
                                    .addLast(new ChannelDuplexHandler() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            LOGGER.info("Connected to server at {}", DESTINATION_ADDRESS.getHostString());
                                            super.channelActive(ctx);
                                        }
                                    });
                        }
                    })
                    .connect(DESTINATION_ADDRESS)
                    .sync()
                    .channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeClientConnections() {
        boolean closed = false;

        try {
            lock.lock();

            CountDownLatch latch = new CountDownLatch(2);
            for (Channel channel : new Channel[]{clientToProxyChannel, proxyToServerChannel}) {
                if (channel != null && channel.isOpen()) {
                    channel.close().addListener(future -> latch.countDown());
                    closed = true;
                } else {
                    latch.countDown();
                }
            }

            clientToProxyChannel = null;
            proxyToServerChannel = null;
        } catch (Exception e) {
            LOGGER.warn("Failed to close client connections.", e);
        } finally {
            lock.unlock();
        }

        if (closed) {
            LOGGER.info("Closed client connections.");
        }
    }

    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }

    public Channel getServerChannel() {
        return serverChannel;
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    private class CloseHandler extends ChannelDuplexHandler {

        private final String name;

        private CloseHandler(String name) {
            this.name = name;
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            closeClientConnections();
            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            LOGGER.warn(String.format("Lost connection to %s.", name), cause);
            ctx.channel().close();
        }
    }
}
