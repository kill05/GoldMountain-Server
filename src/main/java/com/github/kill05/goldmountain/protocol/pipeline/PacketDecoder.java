package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.PlayerController;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if(byteBuf.readableBytes() < 7) return;

        if(byteBuf.getShort(byteBuf.readerIndex()) != PlayerController.MAGIC_BYTES) {
            GMServer.logger.warn(String.format("Found illegal data in the cumulative buffer (%s) ! Clearing...", ByteBufUtil.hexDump(byteBuf)));
            byteBuf.clear();
            return;
        }

        int id = PacketUtils.getPacketId(byteBuf);
        int length = PacketUtils.getPacketLength(byteBuf);
        if(byteBuf.readableBytes() < length) return;

        try {
            Packet packet = PacketRegistry.instance().createInboundPacket(id);
            if (packet != null) {
                PacketSerializer serializer = new PacketSerializer(byteBuf.slice(7, length - 7));
                packet.decode(serializer);
                out.add(packet);

                while (serializer.isReadable()) {
                    Packet subPacket = PacketRegistry.instance().createInboundPacket(serializer.readByte());

                    if (subPacket == null) {
                        serializer.readerIndex(serializer.readerIndex() - 1);
                        GMServer.logger.warn("Found extra unprocessed data while decoding a packet. Clearing buffer...");
                        GMServer.logger.warn("Buffered data: " + ByteBufUtil.hexDump(byteBuf));
                        GMServer.logger.warn("Extra data: " + ByteBufUtil.hexDump(serializer));
                        byteBuf.clear();
                        return;
                    }

                    subPacket.decode(serializer);
                    out.add(subPacket);
                }
            }

        } finally {
            byteBuf.readerIndex(length);
            byteBuf.discardReadBytes();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        if(e instanceof IOException) {
            super.exceptionCaught(ctx, e);
            return;
        }

        ByteBuf buf = internalBuffer();
        buf.markReaderIndex();
        buf.readerIndex(0);
        GMServer.logger.warn(String.format("An exception was caught while decoding a packet. (data: %s)", ByteBufUtil.hexDump(buf)), e);
        buf.resetReaderIndex();
    }
}
