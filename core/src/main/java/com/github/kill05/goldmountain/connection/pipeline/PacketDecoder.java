package com.github.kill05.goldmountain.connection.pipeline;

import com.github.kill05.goldmountain.connection.ConnectionConstants;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.connection.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Decodes packets
 */
public class PacketDecoder extends ByteToMessageDecoder {

    public static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);
    public static final int MAGIC_AND_LENGTH_SIZE = 2 + 4; // (2 magic + 4 length)

    private final PacketRegistry packetRegistry;

    public PacketDecoder(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        if (byteBuf.readableBytes() < MAGIC_AND_LENGTH_SIZE) {
            return;
        }

        // Mark index in case it needs to be reset
        byteBuf.markReaderIndex();

        // Check if the first bytes are the magic bytes.
        // If not, that means the current buffer is invalid and has to be cleared
        if (byteBuf.readShort() != ConnectionConstants.MAGIC_BYTES) {
            byteBuf.resetReaderIndex();
            LOGGER.warn("Found invalid data in the cumulative buffer ({}) ! Clearing...", ByteBufUtil.hexDump(byteBuf));
            byteBuf.clear();
            return;
        }

        int length = byteBuf.readIntLE();

        // If message has not been fully sent yet, reset index and return
        if (byteBuf.readableBytes() + MAGIC_AND_LENGTH_SIZE < length) {
            byteBuf.resetReaderIndex();
            return;
        }

        // Change writer index (max readable index) to avoid reading too much
        byteBuf.markWriterIndex();
        byteBuf.writerIndex(byteBuf.readerIndex() + length - MAGIC_AND_LENGTH_SIZE);

        // Decode all packets
        PacketBuffer serializer = new PacketBuffer(byteBuf);

        while (serializer.isReadable()) {
            byte id = serializer.readByte();

            try {
                Packet packet = packetRegistry.decodePacket(id, serializer);
                out.add(packet);
            } catch (IOException e) {
                LOGGER.warn(String.format("Failed to decode packet. (id: 0x%02x)", id), e);
                LOGGER.warn("full data: {}", ByteBufUtil.hexDump(serializer, 0, serializer.writerIndex()));
                LOGGER.warn("remaining data: {}", ByteBufUtil.hexDump(serializer));

                // Close channel
                //ctx.channel().close();
                return;
            }
        }

        // Discard read bytes and reset writer index
        byteBuf.readerIndex(length);
        byteBuf.discardReadBytes();
        byteBuf.resetWriterIndex();
    }


    /*
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

     */
}
