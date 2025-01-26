package com.github.kill05.goldmountain.connection.packets.out;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.Packet;
import com.github.kill05.goldmountain.entity.Player;

import java.util.function.BiConsumer;

public record AssignPlayerIdPacket(int id) implements Packet {

    public static final BiConsumer<PacketBuffer, AssignPlayerIdPacket> ENCODER = (buf, packet) -> {
        buf.writeShortLE(packet.id());
    };

    public AssignPlayerIdPacket(Player player) {
        this(player.getId());
    }

    @Override
    public String toString() {
        return "AssignPlayerIdPacket{" +
                "tileId=" + id +
                '}';
    }
}
