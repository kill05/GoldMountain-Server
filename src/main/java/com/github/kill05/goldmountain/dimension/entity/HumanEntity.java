package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import org.jetbrains.annotations.Nullable;

public abstract class HumanEntity extends Entity {

    protected PlayerCostume costume;
    protected PlayerCostume fakeCostume;

    protected int targetTileId;


    public HumanEntity(GMServer server) {
        super(server);
    }


    public abstract short getId();

    public abstract PlayerConnection getConnection();



    public PlayerCostume getCostume() {
        return costume != null ? costume : PlayerCostume.DEFAULT;
    }

    public PlayerCostume getFakeCostume() {
        return fakeCostume != null ? fakeCostume : costume;
    }

    public void setFakeCostume(@Nullable PlayerCostume fakeCostume) {
        this.fakeCostume = fakeCostume;
    }


    public int getTargetTileId() {
        return targetTileId;
    }
}
