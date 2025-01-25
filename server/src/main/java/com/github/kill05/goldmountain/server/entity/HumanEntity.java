package com.github.kill05.goldmountain.server.entity;

import com.github.kill05.goldmountain.entity.PlayerCostume;
import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.connection.PlayerConnection;
import com.github.kill05.goldmountain.connection.packets.io.HumanUpdatePacket;
import org.jetbrains.annotations.Nullable;

public abstract class HumanEntity extends Entity {

    protected PlayerCostume costume;
    protected PlayerCostume displayCostume;

    protected int targetTile;

    public HumanEntity(GMServer server) {
        super(server);
    }


    public abstract int getId();

    public abstract PlayerConnection getConnection();

    public abstract void update(HumanUpdatePacket packet);


    public PlayerCostume getCostume() {
        return costume != null ? costume : PlayerCostume.DEFAULT;
    }

    public PlayerCostume getDisplayCostume() {
        return displayCostume != null ? displayCostume : costume;
    }

    public void setDisplayCostume(@Nullable PlayerCostume displayCostume) {
        this.displayCostume = displayCostume;
    }

    public int getTargetTile() {
        return targetTile;
    }
}
