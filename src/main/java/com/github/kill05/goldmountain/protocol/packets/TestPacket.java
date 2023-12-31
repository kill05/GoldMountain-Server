package com.github.kill05.goldmountain.protocol.packets;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class TestPacket implements UnregisteredPacket {

    private byte[] bytes;

    public TestPacket() {
    }

    public TestPacket(String hex) {
        try {
            this.bytes = Hex.decodeHex(hex.replaceAll("\\s", ""));
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeBytes(bytes);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }
}
