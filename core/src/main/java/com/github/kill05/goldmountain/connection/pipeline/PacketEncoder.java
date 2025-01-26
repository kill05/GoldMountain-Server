package com.github.kill05.goldmountain.connection.pipeline;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.connection.packet.PacketRegistry;
import com.github.kill05.goldmountain.connection.packet.PacketUtils;
import com.github.kill05.goldmountain.connection.packet.packets.RawPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketEncoder.class);
    private final PacketRegistry packetRegistry;

    public PacketEncoder(PacketRegistry registry) {
        this.packetRegistry = registry;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        try {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            packetBuffer.writeShort(ConnectionConstants.MAGIC_BYTES);
            packetBuffer.writeInt(0); // the first 2 bytes will be replaced with length

            if (packet instanceof RawPacket rawPacket) {
                packetBuffer.writeInt(rawPacket.packetId());
                packetBuffer.writeBytes(rawPacket.data());
            } else {
                packetRegistry.encodePacket(packetBuffer, packet);
            }

            // Replace bytes at the start with packet length
            PacketUtils.encodePacketLength(packetBuffer);
        } catch (Exception e) {
            LOGGER.warn("Failed to encode packet.", e);
            throw new IOException("Failed to encode packet.", e);
        }
    }
}
