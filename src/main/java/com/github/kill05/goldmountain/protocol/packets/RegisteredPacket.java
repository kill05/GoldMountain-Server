package com.github.kill05.goldmountain.protocol.packets;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class RegisteredPacket {

    private final int id;
    private final Class<? extends Packet> clazz;

    public RegisteredPacket(int id, Class<? extends Packet> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Packet> getPacketClass() {
        return clazz;
    }

    public Packet constructPacket() {
        try {
            return ConstructorUtils.invokeConstructor(clazz);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("Error while creating packet:");
            e.printStackTrace();
            return null;
        }
    }
}
