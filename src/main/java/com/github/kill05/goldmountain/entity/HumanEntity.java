package com.github.kill05.goldmountain.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

public abstract class HumanEntity {

    protected final Vector2f location;
    protected final Vector2f firstTargetLocation;
    protected final Vector2f secondTargetLocation;
    protected final Vector2f thirdTargetLocation;
    protected short speed;

    protected PlayerCostume costume;
    protected PlayerCostume fakeCostume;

    protected int targetTileId;


    public HumanEntity() {
        this.location = new Vector2f();
        this.firstTargetLocation = new Vector2f();
        this.secondTargetLocation = new Vector2f();
        this.thirdTargetLocation = new Vector2f();
    }

    public abstract short getId();

    public abstract PlayerConnection getConnection();

    public abstract DimensionType getDimension();

    protected abstract void setDimension(DimensionType dimension);



    public Vector2f getLocation() {
        return location;
    }

    public Vector2f getFirstTargetLocation() {
        return firstTargetLocation;
    }

    public Vector2f getSecondTargetLocation() {
        return secondTargetLocation;
    }

    public Vector2f getThirdTargetLocation() {
        return thirdTargetLocation;
    }

    public short getSpeed() {
        return speed;
    }


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
