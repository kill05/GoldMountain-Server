package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.IOPacket;

public record DigTilePacket(int tileId, short amount, int damage) implements IOPacket {

    public DigTilePacket(PacketSerializer serializer) {
        this(
                serializer.readInt(),
                serializer.readShortLE(),
                serializer.readIntLE()
        );
    }

    @Override
    public int packetId() {
        return 0x03;
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeIntLE(tileId);
        serializer.writeShortLE(amount);
        serializer.writeIntLE(damage);
    }

}
