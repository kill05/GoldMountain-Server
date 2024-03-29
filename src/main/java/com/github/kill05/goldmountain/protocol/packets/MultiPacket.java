package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

public class MultiPacket implements UnregisteredPacket {

    private final List<Packet> packets;

    public MultiPacket(List<Packet> packets) {
        Validate.notNull(packets, "Packet list can't be null!");
        this.packets = packets;
    }

    public MultiPacket() {
        this(new ArrayList<>());
    }


    @Override
    public void encode(PacketSerializer serializer) {
        PacketRegistry registry = PacketRegistry.instance();
        for(Packet packet : packets) {
            int id = registry.getPacket(packet.getClass()).getId();
            serializer.writeByte(id);
            packet.encode(serializer);
            serializer.writeByte(0x00);
        }
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }
}
