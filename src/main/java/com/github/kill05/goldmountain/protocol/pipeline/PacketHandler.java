package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.DigTilePacket;
import com.github.kill05.goldmountain.protocol.packets.io.PlayerUpdatePacket;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class PacketHandler extends ChannelDuplexHandler {

    private final PlayerConnection connection;

    public PacketHandler(PlayerConnection controller) {
        this.connection = controller;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof Packet packet)) {
            throw new IOException(String.format("Expected packet, found %s", msg.getClass().getName()));
        }

        if(packet instanceof PlayerUpdatePacket playerPacket) {
            connection.handlePlayerUpdate(playerPacket);
            return;
        }

        if(packet instanceof DigTilePacket digPacket) {
            GMServer.logger.info("Tile ID: " + digPacket.tileId());
            GMServer.logger.info("Amount:  " + digPacket.amount());
            GMServer.logger.info("Damage:  " + digPacket.damage());
            return;
        }

        super.channelRead(ctx, msg);
    }
}
