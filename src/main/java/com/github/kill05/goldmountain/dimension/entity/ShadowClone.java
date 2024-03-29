package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import org.jetbrains.annotations.Nullable;

public class ShadowClone extends HumanEntity {

    private final ServerPlayer owner;

    public ShadowClone(ServerPlayer owner) {
        super(owner.server);
        this.owner = owner;
    }

    @Override
    public DimensionType getDimensionType() {
        return owner.getDimensionType();
    }

    @Override
    public int getFloor() {
        return owner.getFloor();
    }

    @Override
    public void teleport(@Nullable DimensionType type) {
        // Ignore, can only teleport with owner
    }

    @Override
    public void descend(boolean bypassLimit) {
        // Ignore, can only teleport with owner
    }


    public short getOwnerId() {
        return owner.getId();
    }

    @Override
    public short getId() {
        return owner.getId();
    }

    @Override
    public PlayerConnection getConnection() {
        return owner.getConnection();
    }

}
