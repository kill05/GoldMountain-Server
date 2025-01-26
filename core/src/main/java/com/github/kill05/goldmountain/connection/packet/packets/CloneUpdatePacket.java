package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.entity.ShadowClone;

public class CloneUpdatePacket extends HumanUpdatePacket {

    private int ownerId; // Unsigned short

    public CloneUpdatePacket(ShadowClone clone) {
        super(clone);
        this.ownerId = clone.getOwnerId();
    }

    public CloneUpdatePacket(PacketBuffer serializer) {
        super(serializer);
    }


    @Override
    public void decodeEnd(PacketBuffer serializer) {
        this.ownerId = serializer.readUnsignedShortLE();
    }

    @Override
    public void encodeEnd(PacketBuffer serializer) {
        serializer.writeShortLE(ownerId);
    }
}
