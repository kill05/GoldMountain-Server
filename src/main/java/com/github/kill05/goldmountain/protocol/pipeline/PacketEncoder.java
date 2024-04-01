package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.protocol.ConnectionController;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import com.github.kill05.goldmountain.protocol.packets.unregistered.UnregisteredPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        try {
            PacketSerializer serializer = new PacketSerializer(byteBuf);
            boolean registered = !(packet instanceof UnregisteredPacket);
            serializer.writeShort(ConnectionController.MAGIC_BYTES);
            serializer.writeInt(0x0000_0000); // the first 2 bytes will be replaced with length

            if(registered) {
                int id = packet.packetId();
                serializer.writeByte(id);
            }

            packet.encode(serializer);
            PacketUtils.encodePacketLength(serializer);
        } catch (Exception e) {
            throw new IOException("Failed to encode packet.", e);
        }
    }
}
