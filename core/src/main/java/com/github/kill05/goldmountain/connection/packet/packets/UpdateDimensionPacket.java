package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.Identifiable;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public record UpdateDimensionPacket(int dimensionId) implements Packet {

    public static final BiConsumer<PacketBuffer, UpdateDimensionPacket> ENCODER = (buf, packet) -> {
        buf.writeShortLE(packet.dimensionId());
    };

    /**
     * Constructor used to decode the packet
     *
     * @param buf the packet buffer
     */
    public UpdateDimensionPacket(@NotNull PacketBuffer buf) {
        this(buf.readShortLE());
    }

    public UpdateDimensionPacket(@NotNull DimensionType dimensionType) {
        this((short) dimensionType.getId());
    }

    public DimensionType getDimension() {
        return Identifiable.fromIdOrUnknown(DimensionType.class, dimensionId);
    }

}
