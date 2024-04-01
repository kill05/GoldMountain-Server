package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;

public record AssignIdPacket(int id) implements Packet {

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(id);
    }

    @Override
    public int packetId() {
        return 0x07;
    }

    @Override
    public String toString() {
        return "PacketOutAssignPlayerId{" +
                "tileId=" + id +
                '}';
    }
}
