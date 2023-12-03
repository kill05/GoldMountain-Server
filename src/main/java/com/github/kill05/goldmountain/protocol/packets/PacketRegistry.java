package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimension;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimensionData;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutStaircaseLocation;

import java.util.*;

public class PacketRegistry {

    private final ServerConnection serverConnection;
    private final Map<Integer, RegisteredPacket> inboundPacketIdMap;
    private final Map<Class<? extends Packet>, RegisteredPacket> packetClassMap;

    public PacketRegistry(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.inboundPacketIdMap = new HashMap<>();
        this.packetClassMap = new HashMap<>();

        register(0x01, PacketInOutPlayerUpdate.class, PacketDirection.BOTH);

        register(0x04, PacketOutDimension.class, PacketDirection.OUTBOUND);
        register(0x05, PacketOutDimensionData.class, PacketDirection.OUTBOUND);
        register(0x06, PacketOutStaircaseLocation.class, PacketDirection.OUTBOUND);

        //register(0x34, PacketInPlayerUpdate.class);
        //register(0x55, PacketInPlayerAndCloneUpdate.class);

        //register(0x09, PacketOutDimension.class);
        //register(0x5d, PacketOutServerPlayerUpdate.class);
    }

    private <T extends Packet> void register(int id, Class<T> packetClass, PacketDirection direction) {
        try {
            packetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Packet %s doesn't have a zero argument constructor.", packetClass), e);
        }

        RegisteredPacket packet = new RegisteredPacket(id, packetClass, direction);
        packetClassMap.put(packetClass, packet);

        if(direction.isInbound()) {
            if(inboundPacketIdMap.containsKey(id))
                throw new IllegalArgumentException(String.format("Duplicate inbound packet id: %s", Integer.toHexString(id)));

            inboundPacketIdMap.put(id, packet);
        }
    }

    public RegisteredPacket getInboundPacket(int id) {
        RegisteredPacket packet = inboundPacketIdMap.get(id);
        if(packet == null) GMServer.logger.warn(String.format("Packet 0x%02x is not registered as an inbound packet.", id));
        return packet;
    }

    public RegisteredPacket getPacket(Class<? extends Packet> clazz) {
        RegisteredPacket packet = packetClassMap.get(clazz);
        if(packet == null) GMServer.logger.warn(String.format("Unregistered packet: %s.", clazz));
        return packet;
    }

    public RegisteredPacket getPacket(Packet packet) {
        return getPacket(packet.getClass());
    }

    public Packet createInboundPacket(int id) {
        RegisteredPacket packet = getInboundPacket(id);
        if(packet != null) return packet.constructPacket();
        return null;
    }

    public Packet createPacket(Class<? extends Packet> clazz) {
        RegisteredPacket packet = getPacket(clazz);
        if(packet != null) return packet.constructPacket();
        return null;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

}
