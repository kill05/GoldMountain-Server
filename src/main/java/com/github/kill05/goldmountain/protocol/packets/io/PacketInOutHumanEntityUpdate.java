package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.dimension.entity.HumanEntity;
import com.github.kill05.goldmountain.dimension.entity.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public abstract class PacketInOutHumanEntityUpdate implements Packet {

    protected short entityId;

    protected Vector2f location;
    protected Vector2f nextLocation;
    protected Vector2f nextLocation1;
    protected Vector2f nextLocation2;
    private short speed;

    private int unknown_0;
    private PlayerCostume costume;
    private int targetTileId;
    private byte unknown_2;
    private short unknown_3;

    public PacketInOutHumanEntityUpdate(HumanEntity human) {
        this.entityId = human.getId();

        this.location = human.getLocation();
        this.nextLocation = human.getFirstTargetLocation();
        this.nextLocation1 = human.getSecondTargetLocation();
        this.nextLocation2 = human.getThirdTargetLocation();
        this.speed = human.getSpeed();

        this.unknown_0 = 0xffff_ffff; // change once you understand what it does
        this.costume = human.getFakeCostume();
        this.targetTileId = 0xffff_ffff; // change once you understand what it does
    }

    public PacketInOutHumanEntityUpdate() {
        this.unknown_0 = 0xffff_ffff;
        this.targetTileId = 0xffff_ffff;
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(entityId);
        encodeLevel(serializer);

        serializer.writeLocation(location);
        serializer.writeLocation(nextLocation);
        serializer.writeLocation(nextLocation1);
        serializer.writeLocation(nextLocation2);
        serializer.writeShortLE(speed);

        serializer.writeInt(unknown_0);
        serializer.writeShortLE(getCostume().getId());
        serializer.writeInt(targetTileId);

        encodeEnd(serializer);

        //todo: encode only if last packet in a multi packet
        serializer.writeByte(0x0d);
        serializer.writeByte(unknown_2);
        serializer.writeShort(unknown_3);
        serializer.writeByte(0x00);
    }

    @Override
    public void decode(PacketSerializer serializer) {
        entityId = serializer.readShortLE();
        decodeLevel(serializer);

        location = serializer.readLocation();
        nextLocation = serializer.readLocation();
        nextLocation1 = serializer.readLocation();
        nextLocation2 = serializer.readLocation();
        speed = serializer.readShortLE();

        unknown_0 = serializer.readInt();
        costume = IdentifiableEnumHelper.fromId(PlayerCostume.class, serializer.readShortLE());
        targetTileId = serializer.readInt();

        decodeEnd(serializer);

        if(serializer.getByte(serializer.readerIndex()) == 0x0d) {
            serializer.readByte(); // ignored, always 0x0d
            unknown_2 = serializer.readByte();
            unknown_3 = serializer.readShortLE();
            serializer.readByte(); // ignored, always 0x00
        }
    }


    public abstract void decodeLevel(PacketSerializer serializer);

    public abstract void encodeLevel(PacketSerializer serializer);

    public abstract void decodeEnd(PacketSerializer serializer);

    public abstract void encodeEnd(PacketSerializer serializer);


    public short getEntityId() {
        return entityId;
    }

    public void setEntityId(short entityId) {
        this.entityId = entityId;
    }


    public Vector2f getLocation() {
        return location;
    }

    public void setLocation(Vector2f location) {
        this.location = location;
    }

    public Vector2f getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(Vector2f nextLocation) {
        this.nextLocation = nextLocation;
    }

    public Vector2f getNextLocation1() {
        return nextLocation1;
    }

    public void setNextLocation1(Vector2f nextLocation1) {
        this.nextLocation1 = nextLocation1;
    }

    public Vector2f getNextLocation2() {
        return nextLocation2;
    }

    public void setNextLocation2(Vector2f nextLocation2) {
        this.nextLocation2 = nextLocation2;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }


    public int getUnknown_0() {
        return unknown_0;
    }

    public void setUnknown_0(int unknown_0) {
        this.unknown_0 = unknown_0;
    }

    public PlayerCostume getCostume() {
        return costume != null ? costume : PlayerCostume.DEFAULT;
    }

    public void setCostume(PlayerCostume costume) {
        this.costume = costume;
    }

    public int getTargetTileId() {
        return targetTileId;
    }

    public void setTargetTileId(int targetTileId) {
        this.targetTileId = targetTileId;
    }

    public byte getUnknown_2() {
        return unknown_2;
    }

    public void setUnknown_2(byte unknown_2) {
        this.unknown_2 = unknown_2;
    }

    public short getUnknown_3() {
        return unknown_3;
    }

    public void setUnknown_3(short unknown_3) {
        this.unknown_3 = unknown_3;
    }

}
