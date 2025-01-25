package com.github.kill05.goldmountain.connection.packets.out;

import com.github.kill05.goldmountain.connection.packets.Packet;

public record RawPacket(int packetId, byte[] data) implements Packet {
}
