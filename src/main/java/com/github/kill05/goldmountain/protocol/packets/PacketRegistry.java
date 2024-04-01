package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.io.CloneUpdatePacket;
import com.github.kill05.goldmountain.protocol.packets.io.DigTilePacket;
import com.github.kill05.goldmountain.protocol.packets.io.PlayerUpdatePacket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PacketRegistry {

    private final Map<Integer, Function<PacketSerializer, ? extends IOPacket>> factoryMap;

    public PacketRegistry() {
        this.factoryMap = new HashMap<>();

        registerInboundPacket(0x01, PlayerUpdatePacket::new);
        registerInboundPacket(0x02, CloneUpdatePacket::new);
        registerInboundPacket(0x03, DigTilePacket::new);

        //registerPacket(0x04, UpdateDimensionPacket.class);
        //registerPacket(0x05, IExecuteActionPacket.class);
        //registerPacket(0x06, CreateStaircasePacket.class);
        //registerPacket(0x07, AssignIdPacket.class);
    }


    public <T extends IOPacket> void registerInboundPacket(int id, Function<PacketSerializer, T> factory) {
        if (factoryMap.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Duplicate inbound packet id: %s", Integer.toHexString(id)));
        }

        factoryMap.put(id, factory);
    }


    protected Function<PacketSerializer, ? extends IOPacket> getPacketFactory(int id) throws IOException {
        Function<PacketSerializer, ? extends IOPacket> factory = factoryMap.get(id);
        if(factory == null) throw new IOException(String.format("Unregistered inbound packet id: %s", id));
        return factory;
    }

    public IOPacket createInboundPacket(int id, PacketSerializer serializer) throws IOException {
        try {
            return getPacketFactory(id).apply(serializer);
        } catch (Exception e) {
            throw new IOException(String.format("Failed to construct inbound packet (id: %s)", id), e);
        }
    }

}
