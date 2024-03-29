package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.dimension.entity.HumanEntity;
import com.github.kill05.goldmountain.dimension.entity.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public abstract class PacketInOutHumanEntityUpdate implements Packet {

    protected short entityId;

    protected Vector2f[] checkpoints;
    private short speed;

    private int unknown_0;
    private PlayerCostume costume;
    private int targetTileId;
    private byte unknown_2;
    private short unknown_3;

    public PacketInOutHumanEntityUpdate(HumanEntity human) {
        this.entityId = human.getId();

        this.checkpoints = human.getCheckpoints();
        this.speed = human.getSpeed();

        this.unknown_0 = 0xffff_ffff; // change once I understand what it does
        this.costume = human.getDisplayCostume();
        this.targetTileId = 0xffff_ffff; // change once I understand what it does
    }

    public PacketInOutHumanEntityUpdate() {
        this.unknown_0 = 0xffff_ffff;
        this.targetTileId = 0xffff_ffff;
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(entityId);
        encodeLevel(serializer);

        for (Vector2f checkpoint : checkpoints) {
            serializer.writeLocation(checkpoint);
        }

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

        checkpoints = new Vector2f[4];
        for(int i = 0; i < 4; i++) {
            checkpoints[i] = serializer.readLocation();
        }

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

    public Vector2f[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Vector2f[] checkpoints) {
        this.checkpoints = checkpoints;
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
