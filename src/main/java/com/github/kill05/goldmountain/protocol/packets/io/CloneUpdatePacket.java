package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.dimension.entity.ShadowClone;
import com.github.kill05.goldmountain.protocol.PacketSerializer;

public class CloneUpdatePacket extends HumanUpdatePacket {

    private int ownerId;

    public CloneUpdatePacket(ShadowClone clone) {
        super(clone);
        this.ownerId = clone.getOwnerId();
    }

    public CloneUpdatePacket(PacketSerializer serializer) {
        super(serializer);
    }


    @Override
    public void decodeEnd(PacketSerializer serializer) {
        ownerId = serializer.readShortLE();
    }

    @Override
    public void encodeEnd(PacketSerializer serializer) {
        serializer.writeShortLE(ownerId);
    }

    @Override
    public int packetId() {
        return 0x02;
    }
}
