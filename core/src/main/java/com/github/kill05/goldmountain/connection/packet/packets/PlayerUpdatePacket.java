package com.github.kill05.goldmountain.connection.packet.packets;

import com.github.kill05.goldmountain.entity.Player;
import com.github.kill05.goldmountain.connection.PacketBuffer;

import java.util.Arrays;

public class PlayerUpdatePacket extends HumanUpdatePacket {

    private long totalLevel; // Unsigned int

    public PlayerUpdatePacket(Player player) {
        super(player);
        this.totalLevel = player.getTotalLevel();
    }

    public PlayerUpdatePacket(PacketBuffer serializer) {
        super(serializer);
    }


    @Override
    public void decodeLevel(PacketBuffer serializer) {
        this.totalLevel = serializer.readUnsignedIntLE();
    }

    @Override
    public void encodeLevel(PacketBuffer serializer) {
        serializer.writeIntLE((int) totalLevel);
    }

    @Override
    public void decodeEnd(PacketBuffer serializer) {
        serializer.readInt();   // ignored, always 0000_0000
        serializer.readShort(); // ignored, always e803 (which is 1000 in base 10, maybe there's a reason)
    }

    @Override
    public void encodeEnd(PacketBuffer serializer) {
        serializer.writeInt(0x0000_0000);
        serializer.writeShort(0xe803);
    }

    /**
     * Get the total level of the player.
     * In the game, it is used to adjust money gain (mentioned in the multiplayer menu)
     *
     * @return the total level (unsigned int)
     */
    public long totalLevel() {
        return totalLevel;
    }


    @Override
    public String toString() {
        return "PacketInOutPlayerUpdate{" +
                "entityId=" + entityId +
                ", totalLevel=" + totalLevel +
                ", checkpoints=" + Arrays.toString(checkpoints) +
                ", speed=" + speed +
                ", costume=" + costume +
                ", targetTileId=" + targetTileId +
                '}';
    }
}
