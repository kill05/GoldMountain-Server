package com.github.kill05.goldmountain.server.connection;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.connection.packets.Packet;
import com.github.kill05.goldmountain.connection.packets.io.DigTilePacket;
import com.github.kill05.goldmountain.connection.packets.io.PlayerUpdatePacket;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class ServerPacketHandler extends ChannelDuplexHandler {

    private final PlayerConnection connection;

    public ServerPacketHandler(PlayerConnection controller) {
        this.connection = controller;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Packet packet)) {
            throw new IOException(String.format("Expected packet, found %s", msg.getClass().getName()));
        }

        if (packet instanceof PlayerUpdatePacket playerPacket) {
            connection.handlePlayerUpdate(playerPacket);
            return;
        }

        if (packet instanceof DigTilePacket digPacket) {
            GMServer.LOGGER.info("Tile ID: {}", digPacket.tileId());
            GMServer.LOGGER.info("Amount:  {}", digPacket.amount());
            GMServer.LOGGER.info("Damage:  {}", digPacket.damage());
            return;
        }

        super.channelRead(ctx, msg);
    }
}
