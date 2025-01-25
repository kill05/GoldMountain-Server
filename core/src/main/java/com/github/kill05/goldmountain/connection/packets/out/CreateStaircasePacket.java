package com.github.kill05.goldmountain.connection.packets.out;

import com.github.kill05.goldmountain.server.dimension.DimensionType;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.proxy.Identifiable;
import com.github.kill05.goldmountain.connection.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

import java.util.function.BiConsumer;

public record CreateStaircasePacket(Vector2f location, int dimensionId) implements Packet {

    public static final BiConsumer<PacketBuffer, CreateStaircasePacket> ENCODER = (serializer, packet) -> {
        serializer.writeLocation(packet.location());
        serializer.writeShortLE(packet.dimensionId());
    };

    @NotNull
    public DimensionType getDimension() {
        return Identifiable.fromIdOrUnknown(DimensionType.class, dimensionId());
    }
}
