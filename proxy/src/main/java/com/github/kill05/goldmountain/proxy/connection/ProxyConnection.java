package com.github.kill05.goldmountain.proxy.connection;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.connection.packets.PacketRegistry;
import com.github.kill05.goldmountain.connection.pipeline.PacketDecoder;
import com.github.kill05.goldmountain.connection.pipeline.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProxyConnection {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProxyConnection.class);
    public static final InetSocketAddress DESTINATION_ADDRESS = new InetSocketAddress("192.168.1.10", ConnectionConstants.PORT);

    private final PacketRegistry packetRegistry;
    private final Lock lock;

    private final Channel serverChannel;
    private Channel clientToProxyChannel;
    private Channel proxyToServerChannel;
    private final EventLoopGroup clientGroup;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public ProxyConnection() {
        this.packetRegistry = new PacketRegistry();
        this.lock = new ReentrantLock();

        this.clientGroup = new NioEventLoopGroup();
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.serverChannel = new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        // Init pipeline
                        initPipeline(channel);
                        channel.pipeline()
                                .addLast("packet_handler", new ProxyPacketHandler())
                                .addLast(new ChannelDuplexHandler() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        try {
                                            lock.lock();
                                            if (proxyToServerChannel != null) {
                                                LOGGER.info("Refusing connection because another client is already connected.");
                                                channel.disconnect();
                                                return;
                                            }

                                            proxyToServerChannel = connectToDestinationServer();
                                            clientToProxyChannel = channel;
                                        } finally {
                                            lock.unlock();
                                        }

                                        super.channelActive(ctx);
                                    }

                                    @Override
                                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                        closeClientConnections(null);
                                        super.channelInactive(ctx);
                                    }

                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        closeClientConnections(cause);
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
     */
    private Channel connectToDestinationServer() {
        try {
            LOGGER.info("Client connected. Connecting to destination server...");

            return new Bootstrap().group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            initPipeline(channel);

                            channel.pipeline().addLast(new ChannelDuplexHandler() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    LOGGER.info("Connected to server at {}", DESTINATION_ADDRESS.getHostString());
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    closeClientConnections(null);
                                    super.channelInactive(ctx);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                    closeClientConnections(cause);
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

    private void closeClientConnections(@Nullable Throwable t) {
        try {
            lock.lock();

            if (clientToProxyChannel != null) {
                clientToProxyChannel.close();
            }

            if (proxyToServerChannel != null) {
                proxyToServerChannel.close();
            }

            clientToProxyChannel = null;
            proxyToServerChannel = null;
        } catch (Exception e) {
            LOGGER.warn("Failed to close client connections.", e);
        } finally {
            lock.unlock();
        }

        LOGGER.info("Closed client connections. Cause: ", t);
    }

    private void initPipeline(Channel channel) {
        channel.pipeline()
                .addLast("packet_decoder", new PacketDecoder(packetRegistry))
                .addLast("packet_encoder", new PacketEncoder(packetRegistry));
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
}
