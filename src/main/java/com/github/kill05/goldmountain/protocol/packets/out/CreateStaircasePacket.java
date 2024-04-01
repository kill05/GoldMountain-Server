package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public record CreateStaircasePacket(Vector2f location, DimensionType dimensionType) implements Packet {

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeLocation(location);
        serializer.writeShortLE(dimensionType.getId());
    }

    @Override
    public int packetId() {
        return 0x06;
    }
}
