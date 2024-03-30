package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.GMServer;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public record RegisteredPacket(int id, Class<? extends Packet> packetClass, PacketDirection direction) {

    public @NotNull Packet constructPacket() throws IOException {
        try {
            return ConstructorUtils.invokeConstructor(packetClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            GMServer.logger.warn(String.format("An exception was caught while constructing a new packet (%s)", packetClass), e);
            throw new IOException();
        }
    }
}
