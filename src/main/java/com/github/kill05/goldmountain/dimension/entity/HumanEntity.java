package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutHumanEntityUpdate;
import org.jetbrains.annotations.Nullable;

public abstract class HumanEntity extends Entity {

    protected PlayerCostume costume;
    protected PlayerCostume displayCostume;

    protected int targetTile;


    public HumanEntity(GMServer server) {
        super(server);
    }


    public abstract short getId();

    public abstract PlayerConnection getConnection();

    public abstract void update(PacketInOutHumanEntityUpdate packet);


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