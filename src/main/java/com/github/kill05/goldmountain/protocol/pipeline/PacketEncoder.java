package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import com.github.kill05.goldmountain.protocol.packets.TestPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import com.github.kill05.goldmountain.protocol.packets.Packet;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PacketRegistry packetRegistry;

    public PacketEncoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        try {
            PacketSerializer serializer = new PacketSerializer(byteBuf);
            serializer.writeShort(ServerConnection.MAGIC_BYTES);
            serializer.writeInt(0x0000_0000); // first 2 bytes will be replaced with length

            if(!(packet instanceof TestPacket)) {
                int id = packetRegistry.getPacket(packet.getClass()).getId();
                serializer.writeByte(id);
            }

            packet.encode(serializer);

            // replace bytes 2 and 3 with length
            serializer.setShort(2, PacketUtils.getEncodedPacketLength(serializer));
        } catch (Exception e) {
            GMServer.logger.warn("Failed to encode packet.", e);
        }
    }
}
