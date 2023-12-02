package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import org.apache.commons.lang3.Validate;

public interface Packet {

    void encode(PacketSerializer serializer);

    void decode(PacketSerializer serializer);
}
