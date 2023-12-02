package com.github.kill05.goldmountain.protocol.packets.out;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.player.PlayerAction;
import com.github.kill05.goldmountain.player.PlayerCostume;
import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public class PacketOutServerPlayerUpdate implements Packet {

    @Override
    public void encode(PacketSerializer serializer) {
        serializer.writeInt(0x00000001);
        serializer.writeShort(0xfeff);
        serializer.writeInt(0xd000000);
        serializer.writeLocation(DimensionType.MINE_FREE.getLoadingZonePos());
        serializer.writeLocation(DimensionType.MINE_FREE.getLoadingZonePos());
        serializer.writeLocation(DimensionType.MINE_FREE.getLoadingZonePos());
        serializer.writeLocation(DimensionType.MINE_FREE.getLoadingZonePos());
        serializer.writeShort(0x1400);
        serializer.writeInt(PlayerAction.NOTHING.getId());
        serializer.writeShort(PlayerCostume.DEFAULT.getId());
        serializer.writeInt(0xffffffff);
        serializer.writeInt(0x00000000);
        serializer.writeShort(0xe803);
        //serializer.writeShort(GMServer.instance().getCurrentTick());
        serializer.writeShort(0x0000);

        serializer.writeInt(0x41000001);
        serializer.writeShort(0x0100);
        serializer.writeInt(0x00000000);
        serializer.writeLocation(new Vector2f(0.5f, 0.5f));
        serializer.writeLocation(new Vector2f(0.5f, 0.5f));
        serializer.writeLocation(new Vector2f(0.5f, 0.5f));
        serializer.writeLocation(new Vector2f(0.5f, 0.5f));
        serializer.writeShort(0x1400);
        serializer.writeInt(PlayerAction.NOTHING.getId());
        serializer.writeShort(PlayerCostume.DEFAULT.getId());
        serializer.writeInt(0xffffffff);
        serializer.writeInt(0x00000000);
        serializer.writeShort(0xe803);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }
}
