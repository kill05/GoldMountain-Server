package com.github.kill05.goldmountain.connection.packets.out.actions;

import com.github.kill05.goldmountain.connection.PacketBuffer;
import com.github.kill05.goldmountain.connection.packets.Packet;
import com.github.kill05.goldmountain.dimension.TileType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

import java.util.function.BiConsumer;

/**
 * A packet used to execute a generic action on the client.<p>
 * There are plenty of use cases for this packet and most of them are unknown.<p>
 * This is probably the most powerful packet in the game and can easily crash the client. Use with care!<p>
 *
 * Some noteworthy use cases are:
 * <li>Placing tiles at a location</li>
 * <li>Playing reward ads on the client</li>
 * <li>Activating MotD</li>
 *
 * @param location the location
 * @param actionCode the action code (unsigned short) (to confirm)
 * @param id an id, used for certain actions (such as placing tiles)
 */
public record ExecuteActionPacket(
        @NotNull Vector2f location,
        int actionCode,
        int id
) implements Packet {

    public static final BiConsumer<PacketBuffer, ExecuteActionPacket> ENCODER = (serializer, packet) -> {
        serializer.writeLocation(packet.location());
        serializer.writeShortLE(packet.actionCode());
        serializer.writeIntLE(packet.id());
    };


    /**
     * Constructs an action packet that shows a tile to the client at the given location
     *
     * @param location the location
     * @param type the tile type
     * @param tileId the tile id
     */
    public ExecuteActionPacket(@NotNull Vector2f location, @NotNull TileType type, int tileId) {
        this(location, type.actionCode(), tileId);

        if (!type.isValid()) {
            throw new IllegalArgumentException("Invalid tile type: " + type);
        }
    }

}
