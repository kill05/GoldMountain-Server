package com.github.kill05.goldmountain.connection.packets.io;

import com.github.kill05.goldmountain.entity.PlayerCostume;
import com.github.kill05.goldmountain.server.dimension.entity.HumanEntity;
import com.github.kill05.goldmountain.server.dimension.entity.PlayerCostume;
import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.IOPacket;
import org.joml.Vector2f;

public abstract class HumanUpdatePacket implements IOPacket {

    protected final int entityId;

    protected final Vector2f[] checkpoints;
    protected final short speed;

    protected final int unknown_0;
    protected final PlayerCostume costume;
    protected final int targetTileId;
    protected final byte unknown_2;
    protected final short unknown_3;

    public HumanUpdatePacket(HumanEntity human) {
        this.entityId = human.getId();

        this.checkpoints = human.getCheckpoints();
        this.speed = human.getSpeed();

        this.unknown_0 = 0xffff_ffff; // change once I understand what this does
        this.costume = human.getDisplayCostume();
        this.targetTileId = 0xffff_ffff; // change once I understand what this does

        this.unknown_2 = 0; // change once I understand what this does
        this.unknown_3 = 0; // change once I understand what this does
    }

    public HumanUpdatePacket(PacketBuffer serializer) {
        entityId = serializer.readShortLE();
        decodeLevel(serializer);

        checkpoints = new Vector2f[4];
        for (int i = 0; i < 4; i++) {
            checkpoints[i] = serializer.readLocation();
        }

        speed = serializer.readShortLE();

        unknown_0 = serializer.readInt();
        costume = Identifiable.fromIdOrUnknown(PlayerCostume.class, serializer.readShortLE());
        targetTileId = serializer.readInt();

        decodeEnd(serializer);

        //todo: change once i understand what this does
        if (serializer.getByte(serializer.readerIndex()) == 0x0d) {
            serializer.readByte(); // ignored, always 0x0d
            unknown_2 = serializer.readByte();
            unknown_3 = serializer.readShortLE();
            serializer.readByte(); // ignored, always 0x00
        } else {
            unknown_2 = 0;
            unknown_3 = 0;
        }
    }

    public void encode(PacketBuffer serializer) {
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

        //TODO: encode only if last packet in a multi packet
        serializer.writeByte(0x0d);
        serializer.writeByte(unknown_2);
        serializer.writeShort(unknown_3);
        serializer.writeByte(0x00);
    }


    public void decodeLevel(PacketBuffer serializer) {

    }

    public void encodeLevel(PacketBuffer serializer) {

    }

    public abstract void decodeEnd(PacketBuffer serializer);

    public abstract void encodeEnd(PacketBuffer serializer);


    public int getEntityId() {
        return entityId;
    }

    public Vector2f[] getCheckpoints() {
        return checkpoints;
    }

    public short getSpeed() {
        return speed;
    }

    public int getUnknown_0() {
        return unknown_0;
    }

    public PlayerCostume getCostume() {
        return costume != null ? costume : PlayerCostume.DEFAULT;
    }

    public int getTargetTile() {
        return targetTileId;
    }

    public byte getUnknown_2() {
        return unknown_2;
    }

    public short getUnknown_3() {
        return unknown_3;
    }

}
