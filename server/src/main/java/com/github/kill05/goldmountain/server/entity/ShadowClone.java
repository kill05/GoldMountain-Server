package com.github.kill05.goldmountain.server.entity;

import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.server.connection.PlayerConnection;
import com.github.kill05.goldmountain.connection.packets.io.HumanUpdatePacket;

public class ShadowClone extends ServerHumanEntity {

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
