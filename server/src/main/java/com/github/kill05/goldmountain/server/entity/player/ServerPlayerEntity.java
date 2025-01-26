package com.github.kill05.goldmountain.server.entity.player;

import com.github.kill05.goldmountain.entity.Player;
import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.entity.ServerHumanEntity;

public abstract class ServerPlayerEntity extends ServerHumanEntity implements Player {

    private final int id;

    public ServerPlayerEntity(GMServer server, int id) {
        super(server);
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

}
