package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.entity.Player;

import java.util.function.BiConsumer;

public record AssignPlayerIdPacket(int id) implements Packet {

    public static final BiConsumer<PacketBuffer, AssignPlayerIdPacket> ENCODER = (buf, packet) -> {
        buf.writeShortLE(packet.id());
    };

    /**
     * Constructor used to decode the packet
     *
     * @param buf the packet buffer
     */
    public AssignPlayerIdPacket(PacketBuffer buf) {
        this(buf.readUnsignedShortLE());
    }

    /**
     * Constructor used to create the packet from the player
     *
     * @param player the player
     */
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
