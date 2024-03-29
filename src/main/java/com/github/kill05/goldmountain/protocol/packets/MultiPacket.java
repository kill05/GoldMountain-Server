package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiPacket implements UnregisteredPacket {

    private final PacketRegistry packetRegistry;
    private final List<Packet> packets;

    public MultiPacket(@NotNull PacketRegistry registry, @NotNull List<Packet> packets) {
        this.packetRegistry = registry;
        this.packets = packets;
    }


    @Override
    public void encode(PacketSerializer serializer) {
        for(Packet packet : packets) {
            int id = packetRegistry.getPacket(packet.getClass()).getId();
            serializer.writeByte(id);
            packet.encode(serializer);
            serializer.writeByte(0x00);
        }
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }
}
