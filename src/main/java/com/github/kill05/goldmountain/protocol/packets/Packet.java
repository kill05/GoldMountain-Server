package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;

public interface Packet {

    void encode(PacketSerializer serializer);

    int packetId();

}
