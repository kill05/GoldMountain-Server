package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;

public class PacketOutAssignPlayerId implements Packet {

    private int id;

    public PacketOutAssignPlayerId(int id) {
        this.id = id;
    }

    public PacketOutAssignPlayerId() {
    }


    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(id);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }

    @Override
    public String toString() {
        return "PacketOutAssignPlayerId{" +
                "id=" + id +
                '}';
    }
}
