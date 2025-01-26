package com.github.kill05.goldmountain.connection;

import com.github.kill05.goldmountain.connection.packet.PacketRegistry;
import com.github.kill05.goldmountain.connection.packet.packets.*;

public class ProxyPacketRegistry extends PacketRegistry {

    public ProxyPacketRegistry() {
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

        registerIOPacket(0x04, UpdateDimensionPacket.class,
                UpdateDimensionPacket.ENCODER,
                UpdateDimensionPacket::new
        );

        registerIOPacket(0x05, ExecuteActionPacket.class,
                ExecuteActionPacket.ENCODER,
                ExecuteActionPacket::new
        );

        registerIOPacket(0x06, CreateStaircasePacket.class,
                CreateStaircasePacket.ENCODER,
                CreateStaircasePacket::new
        );

        registerIOPacket(0x07, AssignPlayerIdPacket.class,
                AssignPlayerIdPacket.ENCODER,
                AssignPlayerIdPacket::new
        );
    }
}
