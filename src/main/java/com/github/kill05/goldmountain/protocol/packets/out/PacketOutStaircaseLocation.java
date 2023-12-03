package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public class PacketOutStaircaseLocation implements Packet {

    private Vector2f location;
    private DimensionType dimensionType;

    public PacketOutStaircaseLocation() {

    }

    public PacketOutStaircaseLocation(Vector2f location, DimensionType dimensionType) {
        this.location = location;
        this.dimensionType = dimensionType;
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeLocation(location);
        serializer.writeByte(dimensionType.getId());
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }


    public Vector2f getLocation() {
        return location;
    }

    public void setLocation(Vector2f location) {
        this.location = location;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }
}
