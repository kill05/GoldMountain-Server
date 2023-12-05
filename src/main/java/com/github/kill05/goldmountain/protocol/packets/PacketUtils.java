package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import io.netty.buffer.ByteBuf;

public class PacketUtils {

    private PacketUtils() {}

    public static int getPacketId(ByteBuf byteBuf) {
        return byteBuf.getByte(6);
    }

    public static int getPacketLength(ByteBuf byteBuf) {
        return byteBuf.getShortLE(2);
    }

    public static void encodePacketLength(PacketSerializer serializer) {
        serializer.setShortLE(2, serializer.readableBytes());
    }
}
