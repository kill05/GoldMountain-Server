package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;

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
