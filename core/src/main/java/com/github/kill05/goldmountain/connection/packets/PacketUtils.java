package com.github.kill05.goldmountain.connection.packets;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import io.netty.buffer.ByteBuf;

public final class PacketUtils {

    private PacketUtils() {}

    public static int getPacketId(ByteBuf byteBuf) {
        return byteBuf.getByte(byteBuf.readerIndex() + 6);
    }

    public static int getPacketLength(ByteBuf byteBuf) {
        return byteBuf.getShortLE( byteBuf.readerIndex() + 2);
    }

    public static void encodePacketLength(PacketBuffer serializer) {
        serializer.setShortLE(2, serializer.readableBytes());
    }
}
