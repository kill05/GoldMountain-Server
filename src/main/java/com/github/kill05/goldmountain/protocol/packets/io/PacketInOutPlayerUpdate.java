package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.entity.ServerPlayer;
import com.github.kill05.goldmountain.protocol.PacketSerializer;

public class PacketInOutPlayerUpdate extends PacketInOutHumanEntityUpdate {

    private int totalLevel;

    public PacketInOutPlayerUpdate() {
    }

    public PacketInOutPlayerUpdate(ServerPlayer player) {
        super(player);
        this.totalLevel = player.getTotalLevel();
    }


    @Override
    public void decodeLevel(PacketSerializer serializer) {
        this.totalLevel = serializer.readIntLE();
    }

    @Override
    public void encodeLevel(PacketSerializer serializer) {
        serializer.writeIntLE(totalLevel);
    }

    @Override
    public void decodeEnd(PacketSerializer serializer) {
        serializer.readInt();   // ignored, always 0000_0000
        serializer.readShort(); // ignored, always e803 (which is 1000 in base 10, maybe there's a reason) (could be game version/protocol version?)
    }

    @Override
    public void encodeEnd(PacketSerializer serializer) {
        serializer.writeInt(0x0000_0000);
        serializer.writeShort(0xe803);
    }


    public int getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
    }

}
