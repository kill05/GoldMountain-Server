package com.github.kill05.goldmountain.connection.packets.io;

import com.github.kill05.goldmountain.server.dimension.entity.ShadowClone;
import com.github.kill05.goldmountain.connection.PacketBuffer;

public class CloneUpdatePacket extends HumanUpdatePacket {

    private int ownerId;

    public CloneUpdatePacket(ShadowClone clone) {
        super(clone);
        this.ownerId = clone.getOwnerId();
    }

    public CloneUpdatePacket(PacketBuffer serializer) {
        super(serializer);
    }


    @Override
    public void decodeEnd(PacketBuffer serializer) {
        this.ownerId = serializer.readShortLE();
    }

    @Override
    public void encodeEnd(PacketBuffer serializer) {
        serializer.writeShortLE(ownerId);
    }
}
