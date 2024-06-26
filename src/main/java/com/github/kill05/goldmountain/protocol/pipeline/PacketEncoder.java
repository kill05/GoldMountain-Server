package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.PlayerController;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import com.github.kill05.goldmountain.protocol.packets.UnregisteredPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private final PlayerController playerController;

    public PacketEncoder(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        try {
            PacketSerializer serializer = new PacketSerializer(byteBuf);
            boolean registered = !(packet instanceof UnregisteredPacket);
            serializer.writeShort(PlayerController.MAGIC_BYTES);
            serializer.writeInt(0x0000_0000); // the first 2 bytes will be replaced with length

            if(registered) {
                int id = playerController.getPacketRegistry().getPacket(packet.getClass()).id();
                serializer.writeByte(id);
            }

            packet.encode(serializer);
            PacketUtils.encodePacketLength(serializer);
        } catch (Exception e) {
            GMServer.logger.warn("Failed to encode packet.", e);
        }
    }
}
