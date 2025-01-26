package com.github.kill05.goldmountain.server.connection;

import com.github.kill05.goldmountain.connection.packet.PacketRegistry;
import com.github.kill05.goldmountain.connection.packet.packets.*;

public class ServerPacketRegistry extends PacketRegistry {

    public ServerPacketRegistry() {
        registerIOPacket(0x01, PlayerUpdatePacket.class,
                (packetSerializer, packet) -> packet.encode(packetSerializer),
                PlayerUpdatePacket::new
        );

        registerIOPacket(0x02, CloneUpdatePacket.class,
                (packetSerializer, packet) -> packet.encode(packetSerializer),
                CloneUpdatePacket::new
        );

        registerIOPacket(0x03, DigTilePacket.class,
                DigTilePacket.ENCODER,
                DigTilePacket::new
        );

        registerOutboundPacket(0x04, UpdateDimensionPacket.class, UpdateDimensionPacket.ENCODER);
        registerOutboundPacket(0x05, ExecuteActionPacket.class, ExecuteActionPacket.ENCODER);
        registerOutboundPacket(0x06, CreateStaircasePacket.class, CreateStaircasePacket.ENCODER);
        registerOutboundPacket(0x07, AssignPlayerIdPacket.class, AssignPlayerIdPacket.ENCODER);
    }

}
