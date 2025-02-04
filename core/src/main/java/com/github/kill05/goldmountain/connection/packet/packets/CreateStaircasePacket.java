package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.Identifiable;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

import java.util.function.BiConsumer;

public record CreateStaircasePacket(Vector2f location, int dimensionId) implements Packet {

    public static final BiConsumer<PacketBuffer, CreateStaircasePacket> ENCODER = (serializer, packet) -> {
        serializer.writeLocation(packet.location());
        serializer.writeShortLE(packet.dimensionId());
    };

    /**
     * Constructor used to decode the packet
     *
     * @param buf the packet buffer
     */
    public CreateStaircasePacket(PacketBuffer buf) {
        this(
                buf.readLocation(),
                buf.readUnsignedShortLE()
        );
    }

    @NotNull
    public DimensionType getDimension() {
        return Identifiable.fromIdOrUnknown(DimensionType.class, dimensionId());
    }
}
