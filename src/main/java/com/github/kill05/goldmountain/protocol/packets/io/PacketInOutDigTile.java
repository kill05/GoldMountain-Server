package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;

public class PacketInOutDigTile implements Packet {

    private int tileId;
    private short amount;
    private int damage;

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeIntLE(tileId);
        serializer.writeShortLE(amount);
        serializer.writeIntLE(damage);
    }

    @Override
    public void decode(PacketSerializer serializer) {
        this.tileId = serializer.readIntLE();
        this.amount = serializer.readShortLE();
        this.damage = serializer.readIntLE();
    }


    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public short getAmount() {
        return amount;
    }

    public void setAmount(short amount) {
        this.amount = amount;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
