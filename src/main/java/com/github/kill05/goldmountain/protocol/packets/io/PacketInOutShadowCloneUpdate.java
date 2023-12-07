package com.github.kill05.goldmountain.protocol.packets.io;

import com.github.kill05.goldmountain.entity.ShadowClone;
import com.github.kill05.goldmountain.protocol.PacketSerializer;

public class PacketInOutShadowCloneUpdate extends PacketInOutHumanEntityUpdate {

    private short ownerId;

    public PacketInOutShadowCloneUpdate(ShadowClone clone) {
        super(clone);
        this.ownerId = clone.getOwnerId();
    }

    public PacketInOutShadowCloneUpdate() {

    }

    @Override
    public void decodeLevel(PacketSerializer serializer) {
        // No level
    }

    @Override
    public void encodeLevel(PacketSerializer serializer) {
        // No level
    }

    @Override
    public void decodeEnd(PacketSerializer serializer) {
        ownerId = serializer.readShortLE();
    }


    @Override
    public void encodeEnd(PacketSerializer serializer) {
        serializer.writeShortLE(ownerId);
    }
}
