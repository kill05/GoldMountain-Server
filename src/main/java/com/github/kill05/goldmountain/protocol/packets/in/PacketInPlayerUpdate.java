package com.github.kill05.goldmountain.protocol.packets.in;

import com.github.kill05.goldmountain.player.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.PacketRegistry;
import org.joml.Vector2f;

public class PacketInPlayerUpdate implements Packet {

    private byte[] bytes;
    private int unknown_0_3;
    private short unknown_4_5;
    private int accountData;

    private Vector2f currentPos;
    private Vector2f futurePos;
    private Vector2f location3;
    private Vector2f location4;
    private short yaw;

    private int currentAction;
    private PlayerCostume costume;
    private int unknown_34_37;

    @Override
    public void encode(PacketSerializer serializer) {

    }

    @Override
    public void decode(PacketSerializer serializer) {
        this.bytes = new byte[serializer.readableBytes()];
        serializer.markReaderIndex();
        serializer.readBytes(this.bytes);
        serializer.resetReaderIndex();

        unknown_0_3 = serializer.readInt();
        unknown_4_5 = serializer.readShort();
        accountData = serializer.readInt();

        currentPos = serializer.readLocation();
        futurePos = serializer.readLocation();
        location3 = serializer.readLocation();
        location4 = serializer.readLocation();
        yaw = serializer.readShort();

        currentAction = serializer.readInt();

        costume = IdentifiableEnumHelper.fromId(PlayerCostume.class, serializer.readShort());
        unknown_34_37 = serializer.readInt();

    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getUnknown_0_3() {
        return unknown_0_3;
    }

    public short getUnknown_4_5() {
        return unknown_4_5;
    }

    public int getAccountData() {
        return accountData;
    }

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public Vector2f getFuturePos() {
        return futurePos;
    }

    public Vector2f getLocation3() {
        return location3;
    }

    public Vector2f getLocation4() {
        return location4;
    }

    public short getYaw() {
        return yaw;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public PlayerCostume getCostume() {
        return costume;
    }

    public int getUnknown_34_37() {
        return unknown_34_37;
    }
}
