package com.github.kill05.goldmountain.entity;

public interface HumanEntity extends Entity {

    int getId();

    short getSpeed();

    PlayerCostume getCostume();

    default PlayerCostume getDisplayCostume() {
        return getCostume();
    }

}
