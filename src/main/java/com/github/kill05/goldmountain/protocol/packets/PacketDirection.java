package com.github.kill05.goldmountain.protocol.packets;

public enum PacketDirection {
    INBOUND,
    OUTBOUND,
    BOTH;

    public boolean isInbound() {
        return this != OUTBOUND;
    }

    public boolean isOutbound() {
        return this != INBOUND;
    }
}
