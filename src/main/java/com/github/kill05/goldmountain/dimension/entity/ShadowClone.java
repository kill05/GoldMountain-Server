package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutHumanEntityUpdate;

public class ShadowClone extends HumanEntity {

    private final ServerPlayer owner;

    public ShadowClone(ServerPlayer owner) {
        super(owner.server);
        this.owner = owner;
    }

    public short getOwnerId() {
        return getId();
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
    public void update(PacketInOutHumanEntityUpdate packet) {

    }

}
