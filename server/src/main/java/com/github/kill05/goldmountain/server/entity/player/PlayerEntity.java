package com.github.kill05.goldmountain.server.entity.player;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.entity.HumanEntity;

public abstract class PlayerEntity extends HumanEntity {

    private final int id;

    public PlayerEntity(GMServer server, int id) {
        super(server);
        this.id = id;
    }

    public abstract int getTotalLevel();

    @Override
    public int getId() {
        return id;
    }

}
