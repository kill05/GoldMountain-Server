package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;

public class PacketOutDimension implements Packet {

    private byte dimensionId;

    public PacketOutDimension() {
    }

    public PacketOutDimension(byte dimensionId) {
        this.dimensionId = dimensionId;
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeByte(dimensionId);
        serializer.writeByte(0x00);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }


    public short getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(byte dimensionId) {
        this.dimensionId = dimensionId;
    }
}
