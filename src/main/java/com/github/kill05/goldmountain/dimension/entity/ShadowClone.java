package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.dimension.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.HumanUpdatePacket;

public class ShadowClone extends HumanEntity {

    private final ServerPlayer owner;

    public ShadowClone(ServerPlayer owner) {
        super(owner.server);
        this.owner = owner;
    }

    public int getOwnerId() {
        return getId();
    }

    @Override
    public int getId() {
        return owner.getId();
    }

    @Override
    public PlayerConnection getConnection() {
        return owner.getConnection();
    }

    @Override
    public void update(HumanUpdatePacket packet) {

    }

}
