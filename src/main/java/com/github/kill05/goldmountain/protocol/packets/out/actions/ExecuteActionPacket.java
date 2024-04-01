package com.github.kill05.goldmountain.protocol.packets.out.actions;

import com.github.kill05.goldmountain.dimension.tile.TileType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public record ExecuteActionPacket(@NotNull Vector2f location, short actionCode, int tileId) implements IExecuteActionPacket {

    public ExecuteActionPacket(Vector2f location, TileType type, int tileId) {
        this(location, type.actionCode(), tileId);
    }

}
