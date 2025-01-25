package com.github.kill05.goldmountain.connection.pipeline;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.IOPacket;
import com.github.kill05.goldmountain.connection.packets.PacketRegistry;
import com.github.kill05.goldmountain.connection.packets.PacketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    public static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);
    private final PacketRegistry packetRegistry;

    public PacketDecoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes() < 7) return;

        if (byteBuf.getShort(byteBuf.readerIndex()) != ConnectionConstants.MAGIC_BYTES) {
            LOGGER.warn("Found invalid data in the cumulative buffer ({}) ! Clearing...", ByteBufUtil.hexDump(byteBuf));
            byteBuf.clear();
            return;
        }

        // Read id and length without consuming the buffer
        int id = PacketUtils.getPacketId(byteBuf);
        int length = PacketUtils.getPacketLength(byteBuf);
        if (byteBuf.readableBytes() < length) return;

        try {
            PacketBuffer serializer = new PacketBuffer(byteBuf.slice(7, length - 7));
            IOPacket packet = packetRegistry.decodePacket(id, serializer);
            out.add(packet);

            while (serializer.isReadable()) {
                try {
                    IOPacket subPacket = packetRegistry.decodePacket(serializer.readByte(), serializer);
                    out.add(subPacket);
                } catch (IOException e) {
                    serializer.readerIndex(serializer.readerIndex() - 1);
                    throw new IOException("Incomplete data stream consumption. Extra data: " + ByteBufUtil.hexDump(serializer));
                }
            }
        } finally {
            // Discard bytes that were supposed to be read in case they were not
            byteBuf.readerIndex(length);
            byteBuf.discardReadBytes();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        if (e instanceof IOException) {
            super.exceptionCaught(ctx, e);
            return;
        }

        // Reset buffer
        ByteBuf buf = internalBuffer();
        buf.markReaderIndex();
        buf.readerIndex(0);

        LOGGER.warn(String.format("An exception was caught while decoding a packet. (data: %s)", ByteBufUtil.hexDump(buf)), e);
        buf.resetReaderIndex();
    }
}
