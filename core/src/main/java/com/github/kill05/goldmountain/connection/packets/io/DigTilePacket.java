package com.github.kill05.goldmountain.connection.packets.io;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.IOPacket;

import java.util.function.BiConsumer;

public record DigTilePacket(int tileId, short amount, int damage) implements IOPacket {

    public static final BiConsumer<PacketBuffer, DigTilePacket> ENCODER = (serializer, packet) -> {
        serializer.writeIntLE(packet.tileId());
        serializer.writeShortLE(packet.amount());
        serializer.writeIntLE(packet.damage());
    };

    public DigTilePacket(PacketBuffer serializer) {
        this(
                serializer.readIntLE(),
                serializer.readShortLE(),
                serializer.readIntLE()
        );
    }

}
