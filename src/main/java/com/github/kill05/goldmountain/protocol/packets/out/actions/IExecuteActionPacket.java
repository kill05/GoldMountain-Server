package com.github.kill05.goldmountain.protocol.packets.out.actions;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public interface IExecuteActionPacket extends Packet {

    @Override
    default void encode(PacketSerializer serializer) {
        serializer.writeLocation(location());
        serializer.writeShortLE(actionCode());
        serializer.writeIntLE(tileId());
    }

    @Override
    default int packetId() {
        return 0x05;
    }

    @NotNull Vector2f location();

    short actionCode();

    int tileId();

}
