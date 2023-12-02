package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import io.netty.buffer.ByteBuf;

public class PacketUtils {

    private PacketUtils() {}

    public static int getPacketLength(ByteBuf byteBuf) {
        byte lengthLeast = byteBuf.getByte(2);
        byte lengthMost = byteBuf.getByte(3);
        return (lengthMost << 8) | (lengthLeast & 0xFF);
    }

    public static int getEncodedPacketLength(PacketSerializer serializer) {
        int length = serializer.readableBytes();
        byte lengthLeast = (byte) (length & 0xFF);
        byte lengthMost = (byte) ((length >> 8) & 0xFF);
        return (lengthLeast << 8 | lengthMost);
    }

    public static int getPacketId(ByteBuf byteBuf) {
        return byteBuf.getByte(6);
    }
}
