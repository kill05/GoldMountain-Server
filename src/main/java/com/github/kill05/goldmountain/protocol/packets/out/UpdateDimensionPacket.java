package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.Identifiable;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.jetbrains.annotations.NotNull;

public record UpdateDimensionPacket(short dimensionId) implements Packet {

    public UpdateDimensionPacket(@NotNull DimensionType dimensionType) {
        this((short) dimensionType.getId());
    }


    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(dimensionId);
    }

    @Override
    public int packetId() {
        return 0x04;
    }


    public DimensionType getDimension() {
        return Identifiable.fromId(DimensionType.class, dimensionId);
    }

}
