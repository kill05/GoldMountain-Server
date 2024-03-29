package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutDigTile;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PacketHandler extends ChannelDuplexHandler {

    private final ServerConnection connection;

    public PacketHandler(ServerConnection connection) {
        this.connection = connection;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof Packet packet)) {
            throw new IOException(String.format("Expected packet, found %s", msg.getClass().getName()));
        }

        if(packet instanceof PacketInOutPlayerUpdate playerPacket) {
            connection.lastUpdate = playerPacket;
        }

        if(packet instanceof PacketInOutDigTile digPacket) {
            GMServer.logger.info("ID:     " + digPacket.getTileId());
            GMServer.logger.info("Amount: " + digPacket.getAmount());
            GMServer.logger.info("Damage: " + digPacket.getDamage());
        }

        super.channelRead(ctx, msg);
    }
}
