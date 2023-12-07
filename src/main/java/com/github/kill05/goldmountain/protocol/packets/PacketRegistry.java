package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutAssignPlayerId;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimension;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimensionData;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutStaircaseLocation;

import java.util.*;

public class PacketRegistry {

    private static PacketRegistry instance;
    private final Map<Integer, RegisteredPacket> inboundPacketIdMap;
    private final Map<Class<? extends Packet>, RegisteredPacket> packetClassMap;


    private PacketRegistry() {
        this.inboundPacketIdMap = new HashMap<>();
        this.packetClassMap = new HashMap<>();

        register(0x01, PacketInOutPlayerUpdate.class, PacketDirection.BOTH);

        register(0x04, PacketOutDimension.class, PacketDirection.OUTBOUND);
        register(0x05, PacketOutDimensionData.class, PacketDirection.OUTBOUND);
        register(0x06, PacketOutStaircaseLocation.class, PacketDirection.OUTBOUND);
        register(0x07, PacketOutAssignPlayerId.class, PacketDirection.OUTBOUND);
    }


    public static PacketRegistry instance() {
        return instance == null ? (instance = new PacketRegistry()) : instance;
    }


    private <T extends Packet> void register(int id, Class<T> packetClass, PacketDirection direction) {
        RegisteredPacket packet = new RegisteredPacket(id, packetClass, direction);

        if(direction.isInbound()) {
            if(inboundPacketIdMap.containsKey(id))
                throw new IllegalArgumentException(String.format("Duplicate inbound packet id: %s", Integer.toHexString(id)));

            try {
                packetClass.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(String.format("Inbound packet %s doesn't have a zero argument constructor.", packetClass), e);
            }

            inboundPacketIdMap.put(id, packet);
        }

        packetClassMap.put(packetClass, packet);
    }

    public RegisteredPacket getInboundPacket(int id) {
        RegisteredPacket packet = inboundPacketIdMap.get(id);
        if(packet == null) GMServer.logger.warn(String.format("Unregistered inbound packet: 0x%02x.", id));
        return packet;
    }

    public RegisteredPacket getPacket(Class<? extends Packet> clazz) {
        RegisteredPacket packet = getPacket0(clazz);
        if(packet == null && !clazz.isInstance(UnregisteredPacket.class)) GMServer.logger.warn(String.format("Unregistered packet: %s.", clazz));
        return packet;
    }

    @SuppressWarnings("unchecked")
    private RegisteredPacket getPacket0(Class<? extends Packet> clazz) {
        RegisteredPacket packet = packetClassMap.get(clazz);
        if(packet != null) return packet;

        Class<?> superclass = clazz.getSuperclass();
        if(!Packet.class.isAssignableFrom(superclass)) return null;

        Class<? extends Packet> superPacketClass = (Class<? extends Packet>) superclass;
        RegisteredPacket superPacket = getPacket0(superPacketClass);
        if(superPacket != null) packetClassMap.put(superPacketClass, superPacket);

        return superPacket;
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

}
