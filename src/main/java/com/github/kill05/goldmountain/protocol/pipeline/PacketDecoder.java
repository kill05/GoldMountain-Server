package com.github.kill05.goldmountain.protocol.pipeline;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.ConnectionController;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.IOPacket;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import com.github.kill05.goldmountain.protocol.packets.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final PacketRegistry packetRegistry;

    public PacketDecoder(ConnectionController connectionController) {
        this.packetRegistry = connectionController.getPacketRegistry();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if(byteBuf.readableBytes() < 7) return;

        if(byteBuf.getShort(byteBuf.readerIndex()) != ConnectionController.MAGIC_BYTES) {
            GMServer.logger.warn(String.format("Found illegal data in the cumulative buffer (%s) ! Clearing...", ByteBufUtil.hexDump(byteBuf)));
            byteBuf.clear();
            return;
        }

        int id = PacketUtils.getPacketId(byteBuf);
        int length = PacketUtils.getPacketLength(byteBuf);
        if(byteBuf.readableBytes() < length) return;

        try {
            PacketSerializer serializer = new PacketSerializer(byteBuf.slice(7, length - 7));
            IOPacket packet = packetRegistry.createInboundPacket(id, serializer);
            out.add(packet);

            while (serializer.isReadable()) {
                try {
                    IOPacket subPacket = packetRegistry.createInboundPacket(serializer.readByte(), serializer);
                    out.add(subPacket);
                } catch (IOException e) {
                    serializer.readerIndex(serializer.readerIndex() - 1);
                    throw new IOException("Incomplete data stream consumption. Extra data: " + ByteBufUtil.hexDump(serializer));
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
