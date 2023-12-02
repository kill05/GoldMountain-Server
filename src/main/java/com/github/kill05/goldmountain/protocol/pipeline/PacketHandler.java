package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.in.PacketInPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimension;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import com.github.kill05.goldmountain.protocol.packets.Packet;

import java.io.IOException;

public class PacketHandler extends ChannelDuplexHandler {

    private final ServerConnection connection;

    public PacketHandler(ServerConnection connection) {
        this.connection = connection;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof Packet packet)) throw new IOException(String.format("Expected packet, found %s", msg.getClass().getName()));

        if(packet instanceof PacketInPlayerUpdate playerPacket) {
            connection.lastUpdate = playerPacket;
        }

        super.channelRead(ctx, msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GMServer.logger.info(String.format("Player (ip: %s) connected.", ctx.channel().remoteAddress()));
        connection.sendPacket(new PacketOutDimension((byte) DimensionType.SPAWN.getId()));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GMServer.logger.info(String.format("Player (ip: %s) disconnected.", ctx.channel().remoteAddress()));

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
