package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;

import java.util.function.BiConsumer;

public record DigTilePacket(int tileId, short amount, int damage) implements Packet {

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
