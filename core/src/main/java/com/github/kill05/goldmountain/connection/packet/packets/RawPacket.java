package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.connection.packet.Packet;

public record RawPacket(int packetId, byte[] data) implements Packet {
}
