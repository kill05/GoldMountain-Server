package com.github.kill05.goldmountain.connection.packets.out;

import com.github.kill05.goldmountain.Identifiable;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.Packet;
import com.github.kill05.goldmountain.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public record UpdateDimensionPacket(int dimensionId) implements Packet {

    public static final BiConsumer<PacketBuffer, UpdateDimensionPacket> ENCODER = (serializer, packet) -> {
        serializer.writeShortLE(packet.dimensionId());
    };

    public UpdateDimensionPacket(@NotNull DimensionType dimensionType) {
        this((short) dimensionType.getId());
    }

    public DimensionType getDimension() {
        return Identifiable.fromIdOrUnknown(DimensionType.class, dimensionId);
    }

}
