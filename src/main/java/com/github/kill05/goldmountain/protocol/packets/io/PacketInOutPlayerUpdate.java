package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.player.Player;
import com.github.kill05.goldmountain.player.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public class PacketInOutPlayerUpdate implements Packet {

    private short playerId;
    private int metadata;

    private Vector2f currentPos;
    private Vector2f futurePos;
    private Vector2f location3;
    private Vector2f location4;
    private short yaw;

    private int currentAction;
    private PlayerCostume costume;
    private int unknown_1;
    private byte clock;
    private short unknown_2;

    public PacketInOutPlayerUpdate() {
    }

    public PacketInOutPlayerUpdate(Player player) {

    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeShortLE(playerId);
        serializer.writeInt(metadata);

        serializer.writeLocation(currentPos);
        serializer.writeLocation(futurePos);
        serializer.writeLocation(location3);
        serializer.writeLocation(location4);
        serializer.writeShort(yaw);

        serializer.writeInt(currentAction);
        serializer.writeShort((costume != null ? costume : PlayerCostume.DEFAULT).getId());
        serializer.writeInt(unknown_1);

        serializer.writeInt(0x0000_0000);
        serializer.writeShort(0xe803);
        serializer.writeByte(0x0d);
        serializer.writeByte(clock);
        serializer.writeShort(unknown_2);
    }

    @Override
    public void decode(PacketSerializer serializer) {
        playerId = serializer.readShort();
        metadata = serializer.readInt();

        currentPos = serializer.readLocation();
        futurePos = serializer.readLocation();
        location3 = serializer.readLocation();
        location4 = serializer.readLocation();
        yaw = serializer.readShort();

        currentAction = serializer.readInt();
        costume = IdentifiableEnumHelper.fromId(PlayerCostume.class, serializer.readShort());
        unknown_1 = serializer.readInt();

        serializer.readInt();   // ignored, always 00000000
        serializer.readShort(); // ignored, always e803
        serializer.readByte();  // ignored, always 0d
        clock = serializer.readByte();
        unknown_2 = serializer.readShort();
    }

    public short getPlayerId() {
        return playerId;
    }

    public void setPlayerId(short playerId) {
        this.playerId = playerId;
    }

    public int getMetadata() {
        return metadata;
    }

    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }

    public Vector2f getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Vector2f currentPos) {
        this.currentPos = currentPos;
    }

    public Vector2f getFuturePos() {
        return futurePos;
    }

    public void setFuturePos(Vector2f futurePos) {
        this.futurePos = futurePos;
    }

    public Vector2f getLocation3() {
        return location3;
    }

    public void setLocation3(Vector2f location3) {
        this.location3 = location3;
    }

    public Vector2f getLocation4() {
        return location4;
    }

    public void setLocation4(Vector2f location4) {
        this.location4 = location4;
    }

    public short getYaw() {
        return yaw;
    }

    public void setYaw(short yaw) {
        this.yaw = yaw;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public PlayerCostume getCostume() {
        return costume;
    }

    public void setCostume(PlayerCostume costume) {
        this.costume = costume;
    }

    public int getUnknown_1() {
        return unknown_1;
    }

    public void setUnknown_1(int unknown_1) {
        this.unknown_1 = unknown_1;
    }

    public byte getClock() {
        return clock;
    }

    public void setClock(byte clock) {
        this.clock = clock;
    }

    public short getUnknown_2() {
        return unknown_2;
    }

    public void setUnknown_2(short unknown_2) {
        this.unknown_2 = unknown_2;
    }
}
