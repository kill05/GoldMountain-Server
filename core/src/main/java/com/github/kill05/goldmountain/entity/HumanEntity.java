package com.github.kill05.goldmountain.entity;

public interface HumanEntity extends Entity {

    int getId();

    int getSpeed();

    PlayerCostume getCostume();

    default PlayerCostume getDisplayCostume() {
        return getCostume();
    }

}
