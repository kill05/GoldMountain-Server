package com.github.kill05.goldmountain.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutShadowCloneUpdate;

public class ShadowClone extends HumanEntity {

    private final ServerPlayer owner;


    public ShadowClone(ServerPlayer owner) {
        this.owner = owner;
    }


    public void update(PacketInOutShadowCloneUpdate packet) {

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

    @Override
    public DimensionType getDimension() {
        return owner.getDimension();
    }

    @Override
    protected void setDimension(DimensionType dimension) {
        owner.setDimension(dimension);
    }
}
