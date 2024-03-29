package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.GMServer;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class RegisteredPacket {

    private final int id;
    private final Class<? extends Packet> packetClass;
    private final PacketDirection direction;

    public RegisteredPacket(int id, Class<? extends Packet> packetClass, PacketDirection direction) {
        this.id = id;
        this.packetClass = packetClass;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Packet> getPacketClass() {
        return packetClass;
    }

    public PacketDirection getDirection() {
        return direction;
    }

    public @NotNull Packet constructPacket() throws IOException {
        try {
            return ConstructorUtils.invokeConstructor(packetClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            GMServer.logger.warn(String.format("An exception was caught while constructing a new packet (%s)", packetClass), e);
            throw new IOException();
        }
    }
}
