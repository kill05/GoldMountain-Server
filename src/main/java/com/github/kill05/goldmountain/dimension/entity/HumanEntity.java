package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import org.jetbrains.annotations.Nullable;

public abstract class HumanEntity extends Entity {

    protected PlayerCostume costume;
    protected PlayerCostume displayCostume;

    protected int targetTileId;


    public HumanEntity(GMServer server) {
        super(server);
    }


    public abstract short getId();

    public abstract PlayerConnection getConnection();



    public PlayerCostume getCostume() {
        return costume != null ? costume : PlayerCostume.DEFAULT;
    }

    public void updateCostume(PlayerCostume costume) {
        this.costume = costume;
    }

    public PlayerCostume getDisplayCostume() {
        return displayCostume != null ? displayCostume : costume;
    }

    public void setDisplayCostume(@Nullable PlayerCostume displayCostume) {
        this.displayCostume = displayCostume;
    }


    public int getTargetTileId() {
        return targetTileId;
    }

    public void updateTargetTileId(int targetTileId) {
        this.targetTileId = targetTileId;
    }
}
