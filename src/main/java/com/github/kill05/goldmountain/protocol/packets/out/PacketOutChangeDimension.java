package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.jetbrains.annotations.Nullable;

public class PacketOutChangeDimension implements Packet {

    private byte dimensionId;

    public PacketOutChangeDimension(@Nullable DimensionType dimensionType) {
        setDimension(dimensionType);
    }

    public PacketOutChangeDimension(int dimID) {
        setDimensionId(dimID);
    }


    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(dimensionId);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }


    public short getDimensionId() {
        return dimensionId;
    }

    public DimensionType getDimension() {
        return IdentifiableEnumHelper.fromId(DimensionType.class, dimensionId);
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = (byte) dimensionId;
    }

    public void setDimension(@Nullable DimensionType dimensionType) {
        this.dimensionId = (byte) (dimensionType != null ? dimensionType.getId() : DimensionType.SPAWN.getId());
    }
}
