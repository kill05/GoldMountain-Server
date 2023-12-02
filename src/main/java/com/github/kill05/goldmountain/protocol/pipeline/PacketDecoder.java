package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final ServerConnection connection;

    public PacketDecoder(ServerConnection connection) {
        this.connection = connection;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if(byteBuf.readableBytes() < 7) return;

        if(byteBuf.getShort(0) != ServerConnection.MAGIC_BYTES) {
            GMServer.logger.warn(String.format("Found illegal data in the cumulative buffer (%s) ! Clearing...", ByteBufUtil.hexDump(byteBuf)));
            byteBuf.clear();
            return;
        }

        int id = PacketUtils.getPacketId(byteBuf);
        int length = PacketUtils.getPacketLength(byteBuf);

        if(byteBuf.readableBytes() < length) return;

        Packet packet = connection.getPacketRegistry().createPacket(id);
        if(packet != null) {
            packet.decode(new PacketSerializer(byteBuf.slice(7, length)));
            out.add(packet);
        }

        byteBuf.readerIndex(length);
        byteBuf.discardReadBytes();
    }


}
