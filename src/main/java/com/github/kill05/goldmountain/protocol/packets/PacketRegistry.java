package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimension;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimensionData;

import java.util.*;

public class PacketRegistry {

    private final ServerConnection serverConnection;
    private final Map<Integer, RegisteredPacket> packetIdMap;
    private final Map<Class<? extends Packet>, RegisteredPacket> packetClassMap;

    public PacketRegistry(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
        this.packetIdMap = new HashMap<>();
        this.packetClassMap = new HashMap<>();

        register(0x01, PacketInOutPlayerUpdate.class);

        register(0x04, PacketOutDimension.class);
        register(0x05, PacketOutDimensionData.class);

        //register(0x34, PacketInPlayerUpdate.class);
        //register(0x55, PacketInPlayerAndCloneUpdate.class);

        //register(0x09, PacketOutDimension.class);
        //register(0x5d, PacketOutServerPlayerUpdate.class);
    }

    private <T extends Packet> void register(int id, Class<T> packetClass) {
        if(packetIdMap.containsKey(id))
            throw new IllegalArgumentException(String.format("Duplicate packet id: %s", Integer.toHexString(id)));

        try {
            packetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Packet %s doesn't have a zero argument constructor.", packetClass), e);
        }

        RegisteredPacket packet = new RegisteredPacket(id, packetClass);
        packetIdMap.put(id, packet);
        packetClassMap.put(packetClass, packet);
    }

    public RegisteredPacket getRegistered(int id) {
        RegisteredPacket packet = packetIdMap.get(id);
        if(packet == null) GMServer.logger.warn(String.format("Packet 0x%02x is not registered.", id));
        return packet;
    }

    public RegisteredPacket getRegistered(Class<? extends Packet> clazz) {
        RegisteredPacket packet = packetClassMap.get(clazz);
        if(packet == null) GMServer.logger.warn(String.format("Packet %s is not registered.", clazz));
        return packet;
    }

    public RegisteredPacket getRegistered(Packet packet) {
        return getRegistered(packet.getClass());
    }

    public Packet createPacket(int id) {
        RegisteredPacket packet = getRegistered(id);
        if(packet != null) return packet.constructPacket();
        return null;
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

}
